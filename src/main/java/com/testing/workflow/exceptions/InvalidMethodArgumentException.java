package com.testing.workflow.exceptions;

public class InvalidMethodArgumentException extends Exception {
    public InvalidMethodArgumentException(String reason) {
        super(reason);
    }
}
