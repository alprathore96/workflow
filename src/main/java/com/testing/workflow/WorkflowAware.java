package com.testing.workflow;

import com.testing.workflow.context.WorkflowContext;

public interface WorkflowAware {
    void setWorkflowContext(WorkflowContext workflowContext);
    WorkflowContext getWorkflowContext();
}
