package com.testing.workflow.context;

import com.testing.workflow.models.Workflow;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WorkflowContext extends Context {
    private Workflow workflow;
    private List<OperationContext> operationContexts;
    private double currOperation;
    private double nextOperation;

    public WorkflowContext(Workflow workflow) {
        super();
        this.workflow = workflow;
        this.currOperation = 1;
        this.operationContexts = new ArrayList<>();
    }

    public Optional<OperationContext> getOperationContextById(double id) {
        return operationContexts.stream().filter(oc -> (oc.getOperation() != null && oc.getOperation().getId() == id))
                .findAny();
    }

    public Optional<OperationContext> getCurrentOperationContext() {
        return this.getOperationContextById(this.getCurrOperation());
    }

    public Workflow getWorkflow() {
        return workflow;
    }

    public void setWorkflow(Workflow workflow) {
        this.workflow = workflow;
    }

    public double getCurrOperation() {
        return currOperation;
    }

    public void setCurrOperation(double currOperation) {
        this.currOperation = currOperation;
    }

    public double getNextOperation() {
        return nextOperation;
    }

    public void setNextOperation(double nextOperation) {
        this.nextOperation = nextOperation;
    }

    public List<OperationContext> getOperationContexts() {
        return operationContexts;
    }

    public void setOperationContexts(List<OperationContext> operationContexts) {
        this.operationContexts = operationContexts;
    }
}
