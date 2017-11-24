package com.testing.workflow;

import com.testing.config.CacheLibraryConfig;
import com.testing.models.CallableObject;
import com.testing.services.operations.OperationFactory;
import com.testing.workflow.context.OperationContext;
import com.testing.workflow.context.WorkflowContext;
import com.testing.workflow.exceptions.InvalidMethodArgumentException;
import com.testing.workflow.exceptions.InvalidOperationPathException;
import com.testing.workflow.exceptions.WorkflowExecutionException;
import com.testing.workflow.models.NotNull;
import com.testing.workflow.models.Workflow;
import com.testing.workflow.models.operation.Operation;
import com.testing.workflow.models.operation.State;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

@Service
@Scope("prototype")
public class WorkflowRunner {

    private static final Logger LOGGER = Logger.getLogger(WorkflowRunner.class);

    @Autowired
    OperationFactory operationFactory;
    @Autowired
    CacheLibraryConfig cacheLibraryConfig;
    @Autowired
    ApplicationContext context;

    private WorkflowContext workflowContext;

    public void triggerWorkflow(String requestId, Map<String, Object> workflowAttributes) throws InvalidMethodArgumentException, InvalidOperationPathException, IllegalAccessException, InstantiationException, InvocationTargetException, ClassNotFoundException, WorkflowExecutionException {
        Objects.requireNonNull(workflowContext, "Could not find workflow context.");
        Workflow workflow = workflowContext.getWorkflow();
        workflowContext.setAttribute("requestId", requestId);
        if (workflowAttributes != null) {
            workflowAttributes.forEach(workflowContext::setAttribute);
        }
        workflowContext.setNextOperation(workflowContext.getCurrOperation());
        double previousOperation = -1;
        while (workflowContext.getNextOperation() != -1) {
            workflowContext.setCurrOperation(workflowContext.getNextOperation());
            Optional<Operation> operationOptional = workflow.getOperation(workflowContext.getNextOperation());
            if (operationOptional.isPresent()) {
                Operation operation = operationOptional.get();
                operation.setWorkflowContext(workflowContext);
                OperationContext operationContext = new OperationContext(operation);
                workflowContext.getOperationContexts().add(operationContext);
                String path = operation.getPath();
                Map<String, Object> inputs = operation.getInputs();
                Object[] parameters = new Object[inputs.keySet().size()];
                int i = 0;
                for (Map.Entry<String, Object> input : inputs.entrySet()) {
                    parameters[i++] = input.getValue();
                }
                parameters = enrichParameters(workflowContext, parameters, operation.getId(), previousOperation);
                Object bean = null;
                Method method = null;
                if (operationFactory.operationMap.containsKey(path)) {
                    CallableObject callableObject = operationFactory.operationMap.get(path);
                    method = callableObject.getMethod();
                    bean = getClassBean(callableObject.getClazz());
                } else {
                    if (!path.contains(".")) {
                        throw new InvalidOperationPathException(String.format("Could not find method for %s", path));
                    }
                    String classPath = path.substring(0, path.lastIndexOf('.'));
                    String methodName = path.substring(path.lastIndexOf('.') + 1);
                    Class clazz = cacheLibraryConfig.forName(classPath);
                    Optional<Method> methodOptional = Arrays.stream(clazz.getDeclaredMethods()).filter(m -> m.getName()
                            .equals(methodName)).findAny();
                    if (methodOptional.isPresent()) {
                        method = methodOptional.get();
                        bean = getClassBean(clazz);
                    } else {
                        throw new InvalidOperationPathException(String.format("Could not load path: %s", path));
                    }
                }
                if ( bean != null && method != null ) {
                    injectWorkflowContext(bean, workflowContext);
                    try {
                        validateParameters(method, parameters);
                        Object result = method.invoke(bean, parameters);
                        operationContext.setAttribute("result", result);
                    } catch (Exception e) {
                        LOGGER.warn(String.format("Failed execution of operation %s for workflow %s during request " +
                                "%s due to exception: ", operation.getId(), workflowContext.getWorkflow().getId()
                        , workflowContext.getAttribute("request_id")), e);
                        operationContext.setState(State.FAILED);
                        operationContext.setException(e);
                        if ( operation.getErrorNext() == null || operation.getErrorNext() == -1 ) {
                            throw new WorkflowExecutionException(e.getLocalizedMessage());
                        }
                    }
                }
                workflowContext.setNextOperation(operation.getNext());
                previousOperation = operation.getId();
            } else {
                break;
            }
        }
    }

    public Object getClassBean(Class clazz) throws IllegalAccessException, InstantiationException {
        if ( clazz == null ) {
            return null;
        }
        try {
            return context.getBean(clazz);
        } catch (Exception e) {
            return clazz.newInstance();
        }
    }

    private void injectWorkflowContext(Object object, WorkflowContext workflowContext) throws IllegalAccessException, InstantiationException {
        if ( WorkflowAware.class.isAssignableFrom(object.getClass()) ) {
            WorkflowAware workflowAware = (WorkflowAware) object;
            workflowAware.setWorkflowContext(workflowContext);
        }
    }

    private Object[] enrichParameters(WorkflowContext workflowContext, Object[] parameters, double currOperation
            , double previousOperation) throws InvalidMethodArgumentException {
        Object[] enrichedParameters = new Object[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            enrichedParameters[i] = parameters[i];
            if (parameters[i] instanceof Map) {
                enrichMapRecursively(workflowContext, (Map) parameters[i], currOperation, previousOperation);
            } else if (parameters[i] instanceof String) {
                enrichedParameters[i] = enrichScopedParameters(workflowContext, parameters[i].toString(), currOperation, previousOperation);
            }
        }
        return enrichedParameters;
    }

    private void validateParameters(Method method, Object[] parameters) throws InvalidMethodArgumentException {
        if ( method == null || parameters == null ) {
            LOGGER.warn("Could not validate parameters because of null parameters.");
            return;
        }
        Parameter[] methodParameters = method.getParameters();
        if ( parameters.length != methodParameters.length ) {
            LOGGER.warn("Could not validate parameters due to wrong length.");
            return;
        }
        for ( int i = 0; i < parameters.length; i++ ) {
            Object parameter = parameters[i];
            Parameter methodParameter = methodParameters[i];
            NotNull[] notNulls = methodParameter.getAnnotationsByType(NotNull.class);
            if ( notNulls != null ) {
                if ( parameter == null ) {
                    throw new InvalidMethodArgumentException(String.format("Null found for non-null argument %s for method %s"
                            , methodParameter.getName(), method.getName()));
                }
            }
        }
    }

    private void enrichMapRecursively(WorkflowContext workflowContext, Map<String, Object> map, double currOperation
            , double previousOperation) throws InvalidMethodArgumentException {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            Object value = entry.getValue();
            if (value instanceof Map) {
                enrichMapRecursively(workflowContext, (Map<String, Object>) value, currOperation, previousOperation);
            } else if (value instanceof String) {
                entry.setValue(enrichScopedParameters(workflowContext, value.toString(), currOperation, previousOperation));
            }
        }
    }

    private Object enrichScopedParameters(WorkflowContext workflowContext, String strParameter, double currOperation
            , double previousOperation) throws InvalidMethodArgumentException {
        Object parameter = strParameter;
        if (strParameter.contains("wc:")) {
            String scopeAttributeName = strParameter.substring(strParameter.indexOf("wc:") + 3);
            parameter = workflowContext.getAttribute(scopeAttributeName);
        } else if (strParameter.contains("poc:")) {
            if (currOperation == 1) {
                throw new InvalidMethodArgumentException(String.format("Cannot extract operand %s for first method invocation."
                        , strParameter));
            }
            String scopeAttributeName = strParameter.substring(strParameter.indexOf("poc:") + 4);
            List<OperationContext> operationContexts = workflowContext.getOperationContexts();
            Optional<OperationContext> previousOperationContextOptional = operationContexts.stream().filter(oc
                    -> oc.getOperation().getId() == previousOperation).findAny();
            if (previousOperationContextOptional.isPresent()) {
                OperationContext operationContext = previousOperationContextOptional.get();
                parameter = operationContext.getAttribute(scopeAttributeName);
            } else {
                throw new InvalidMethodArgumentException(String.format("Could not find previous operation %s", previousOperation));
            }
        }
        return parameter;
    }

    public WorkflowContext getWorkflowContext() {
        return workflowContext;
    }

    public void setWorkflowContext(WorkflowContext workflowContext) {
        this.workflowContext = workflowContext;
    }
}
