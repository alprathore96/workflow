package com.testing.workflow.context;

import java.util.HashMap;
import java.util.Map;

public abstract class Context {
    private Map<String, Object> scope;

    public Context() {
        this.scope = new HashMap<>();
    }

    public Map<String, Object> getScope() {
        return scope;
    }

    public void setScope(Map<String, Object> scope) {
        this.scope = scope;
    }

    public Object getAttribute(String attributeName) {
        return this.getScope().get(attributeName);
    }

    public void setAttribute(String attributeName, Object value) {
        this.getScope().put(attributeName, value);
    }
}
