package com.testing.services.operations;

import com.testing.services.converters.HttpMethodConverter;
import com.testing.workflow.WorkflowOperation;
import com.testing.workflow.context.WorkflowContext;
import com.testing.workflow.models.NotNull;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Map;

@Service
@Scope("prototype")
public class ApiOperation extends WorkflowOperation {

    private static final Logger LOGGER = Logger.getLogger(ApiOperation.class);
    private WorkflowContext workflowContext;

    public ResponseEntity call(@NotNull String url, @NotNull String method, Map<String, String> queryParams, Map<String, String> headers
            , String payload, Map<String, Object> params) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders httpHeaders = new HttpHeaders();
            headers.forEach((key, value) -> httpHeaders.put(key, Arrays.asList(value)));
            HttpEntity<String> entity = new HttpEntity<>("parameters", httpHeaders);

            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethodConverter.convert(method), entity, String.class);
            this.setOperationAttribute("api_result", responseEntity);
            return responseEntity;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
