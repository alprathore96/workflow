package com.testing.workflow.context;

import com.testing.workflow.models.operation.Operation;
import com.testing.workflow.models.operation.State;

public class OperationContext extends Context {

    private Operation operation;
    private State state;
    private Exception exception;

    public OperationContext(Operation operation) {
        this.operation = operation;
        this.state = State.INITIALIZED;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}
