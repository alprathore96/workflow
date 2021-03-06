package com.testing.workflow;

import com.testing.workflow.context.OperationContext;
import com.testing.workflow.context.WorkflowContext;
import com.testing.workflow.exceptions.InvalidMethodArgumentException;

import java.util.Map;
import java.util.Optional;

public abstract class WorkflowOperation implements WorkflowAware {
    protected WorkflowContext workflowContext;

    public abstract Object operate(Map params) throws InvalidMethodArgumentException;

    @Override
    public void setWorkflowContext(WorkflowContext workflowContext) {
        this.workflowContext = workflowContext;
    }

    @Override
    public WorkflowContext getWorkflowContext() {
        return this.workflowContext;
    }

    public void setOperationAttribute(String key, Object object) {
        Optional<OperationContext> currentOperationContextOptional = this.getWorkflowContext().getCurrentOperationContext();
        if ( currentOperationContextOptional.isPresent() ) {
            OperationContext operationContext = currentOperationContextOptional.get();
            operationContext.setAttribute(key, object);
        }
    }
}
