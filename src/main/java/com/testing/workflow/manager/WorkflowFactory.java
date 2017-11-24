package com.testing.workflow.manager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.testing.services.operations.OperationFactory;
import com.testing.workflow.WorkflowRunner;
import com.testing.workflow.context.WorkflowContext;
import com.testing.workflow.exceptions.InvalidMethodArgumentException;
import com.testing.workflow.exceptions.InvalidOperationPathException;
import com.testing.workflow.exceptions.WorkflowExecutionException;
import com.testing.workflow.models.Workflow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
public class WorkflowFactory {
    private Set<Workflow> workflowSet;

    @Autowired
    OperationFactory operationFactory;
    @Autowired
    ApplicationContext context;

    @PostConstruct
    public void initialize() {
        workflowSet = new HashSet<>();
    }

    public void init() {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            Workflow workflow = mapper.readValue(new File(WorkflowFactory.class.getClassLoader()
                    .getResource("dev/wfConfig/testWf.yaml").getFile()), Workflow.class);
            workflow.enrichConstants();
            workflowSet.add(workflow);
            System.out.println("Workflow factory initialized.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Set<Workflow> getWorkflows() {
        return workflowSet;
    }

    public void triggerWorkflow(String id, String requestId, Map<String, Object> workflowAttributes) throws InvalidMethodArgumentException, InvalidOperationPathException, WorkflowExecutionException {
        Optional<Workflow> workflowOptional = workflowSet.stream().filter(w -> w.getId().equals(id)).findAny();
        if ( workflowOptional.isPresent() ) {
            Workflow workflow = workflowOptional.get();
            WorkflowContext workflowContext = new WorkflowContext(workflow);
            WorkflowRunner workflowRunner = context.getBean(WorkflowRunner.class);
            workflowRunner.setWorkflowContext(workflowContext);
            try {
                workflowRunner.triggerWorkflow(requestId, workflowAttributes);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
