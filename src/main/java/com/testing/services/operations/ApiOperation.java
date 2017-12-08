package com.testing.services.operations;

import com.testing.services.converters.HttpMethodConverter;
import com.testing.workflow.WorkflowOperation;
import com.testing.workflow.context.WorkflowContext;
import com.testing.workflow.exceptions.InvalidMethodArgumentException;
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
import java.util.Objects;

@Service
@Scope("prototype")
public class ApiOperation extends WorkflowOperation {

    private static final Logger LOGGER = Logger.getLogger(ApiOperation.class);
    private WorkflowContext workflowContext;

    public ResponseEntity call(@NotNull String url, @NotNull String method, Map<String, String> queryParams, Map<String, String> headers
            , String payload, Map<String, Object> parameters) {
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

    @Override
    public Object operate(Map params) throws InvalidMethodArgumentException {
        String url = (String) params.get("url");
        if (Objects.isNull(url)) {
            throw new InvalidMethodArgumentException("URL cannot be null for api operation.");
        }
        String method = (String) params.get("method");
        if ( method == null ) {
            method = "GET";
        }
        Map<String, String> queryParams = (Map<String, String>) params.get("queryParams");
        Map<String, String> headers = (Map<String, String>) params.get("headers");
        String payload = (String) params.get("payload");
        Map<String, Object> parameters = (Map<String, Object>) params.get("parameters");
        return call(url, method, queryParams, headers, payload, parameters);
    }
}
