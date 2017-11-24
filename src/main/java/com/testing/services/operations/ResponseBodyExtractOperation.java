package com.testing.services.operations;

import com.testing.workflow.WorkflowOperation;
import com.testing.workflow.context.OperationContext;
import com.testing.workflow.models.NotNull;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Scope("prototype")
public class ResponseBodyExtractOperation extends WorkflowOperation {

    public String extractBody(@NotNull ResponseEntity response) {
        if( response.getBody() == null ) {
            return null;
        }
        String body = response.getBody().toString();
        Optional<OperationContext> operationContextOptional = this.getWorkflowContext().getCurrentOperationContext();
        if ( operationContextOptional.isPresent() ) {
            OperationContext operationContext = operationContextOptional.get();
            operationContext.setAttribute("response_body", body);
        }
        return body;
    }
}
