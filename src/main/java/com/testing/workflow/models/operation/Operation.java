package com.testing.workflow.models.operation;

import java.util.LinkedHashMap;
import java.util.Map;

public class Operation {
    private double id;
    private String name;
    private String path;
    private double next;
    private Double errorNext;
    private Map<String, Object> inputs;
    private boolean param;

    public Operation() {
        this.id = -1;
        inputs = new LinkedHashMap<>();
        param = false;
    }

    public double getId() {
        return id;
    }

    public void setId(double id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Map<String, Object> getInputs() {
        return inputs;
    }

    public void setInputs(Map<String, Object> inputs) {
        this.inputs = inputs;
    }

    public double getNext() {
        return next;
    }

    public void setNext(double next) {
        this.next = next;
    }

    public Double getErrorNext() {
        return errorNext;
    }

    public void setErrorNext(Double errorNext) {
        this.errorNext = errorNext;
    }

    public boolean isParam() {
        return param;
    }

    public void setParam(boolean param) {
        this.param = param;
    }
}
