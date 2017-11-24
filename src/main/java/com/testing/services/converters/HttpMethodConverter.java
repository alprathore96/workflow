package com.testing.services.converters;

import org.springframework.http.HttpMethod;

public class HttpMethodConverter {

    public static HttpMethod convert(String method) {
        if ( method.equalsIgnoreCase("get") ) {
            return HttpMethod.GET;
        } else if ( method.equalsIgnoreCase("post") ) {
            return HttpMethod.POST;
        } else if ( method.equalsIgnoreCase("DELETE") ) {
            return HttpMethod.DELETE;
        } else if ( method.equalsIgnoreCase("PUT") ) {
            return HttpMethod.PUT;
        }
        return null;
    }
}
