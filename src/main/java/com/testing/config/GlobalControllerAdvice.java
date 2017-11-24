package com.testing.config;

import com.testing.services.generators.Generator;
import com.testing.workflow.exceptions.WorkflowExecutionException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice(basePackages = "com.testing")
public class GlobalControllerAdvice {
    private static final Logger LOGGER = Logger.getLogger(GlobalControllerAdvice.class);

    @Autowired
    private Generator randomUuidGenerator;

    @ModelAttribute("requestId")
    public String injectRequetId() {
        return randomUuidGenerator.generate().toString();
    }

    @ExceptionHandler(WorkflowExecutionException.class)
    public ResponseEntity handleWorkflowException(WorkflowExecutionException e) {
       return new ResponseEntity(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
