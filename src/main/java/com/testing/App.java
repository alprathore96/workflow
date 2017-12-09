package com.testing;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) {
        String json = "{\"a\": [5,10], \"b\": {\"one\": 1, \"two\": [2, 4]}}";
        ObjectMapper mapper = new ObjectMapper();
        try {
            Map<String, Object> map = mapper.readValue(json, new TypeReference<HashMap>() {
            });
//            System.out.println(map);
//            Object obj = extractKeyFromMap(map, "b.two");
//            System.out.println(obj);

            json = "{\"a\":[5,10],\"b\":{\"one\":1,\"two\":[{\"three\":3,\"four\":4},{\"three\":4,\"four\":5}]},\"c\":" +
                    "{\"d\":[{\"five\":5,\"six\":{\"seven\":8}},{\"five\":6,\"six\":{\"seven\":9}}]}}";
            map = mapper.readValue(json, new TypeReference<HashMap>() {
            });
            System.out.println(map);

            Map<String, Object> destinationMap = new HashMap<>();
//            mapping(map, destinationMap, "b.two", "b.two");
//            mapping(map, destinationMap, "a", "b.two[i].a");
//            mapping(map, destinationMap, "c.d[i].six.seven", "b.two[i].y");
            System.out.println(destinationMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
