package com.testing.workflow.exceptions;

public class WorkflowExecutionException extends RuntimeException {
    public WorkflowExecutionException(String reason) {
        super(reason);
    }
}
