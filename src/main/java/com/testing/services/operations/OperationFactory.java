package com.testing.services.operations;

import com.testing.models.CallableObject;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Component
public class OperationFactory {
    public Map<String, CallableObject> operationMap;

    @PostConstruct
    public void initialize() {
        operationMap = new HashMap<>();
    }

    public void init() {
        try {
            Class apiClass = Class.forName("com.testing.services.operations.ApiOperation");
            Method apiMethod = getMethod(apiClass, "call");
            CallableObject apiCallable = new CallableObject(apiClass, apiMethod);
            operationMap.put("p:api", apiCallable);

            Class responseBodyExtractClass = Class.forName("com.testing.services.operations.ResponseBodyExtractOperation");
            Method responseBodyExtractMethod = getMethod(responseBodyExtractClass, "extractBody");
            CallableObject responseBodyExtractCallable = new CallableObject(responseBodyExtractClass, responseBodyExtractMethod);
            operationMap.put("p:rbec", responseBodyExtractCallable);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Method getMethod(Class clazz, String methodName) {
        Method[] declaredMethods = clazz.getDeclaredMethods();
        for ( Method method : declaredMethods ) {
            if ( method.getName().equals(methodName) ) {
                return method;
            }
        }
        return null;
    }
}
