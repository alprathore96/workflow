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
            Map<String, Object> map = mapper.readValue(json, new TypeReference<HashMap>() {});
//            System.out.println(map);
//            Object obj = extractKeyFromMap(map, "b.two");
//            System.out.println(obj);

            json = "{\"a\":[5,10],\"b\":{\"one\":1,\"two\":[{\"three\":3,\"four\":4},{\"three\":4,\"four\":5}]},\"c\":" +
                    "{\"d\":[{\"five\":5,\"six\":6},{\"five\":6,\"six\":7}]}}";
            map = mapper.readValue(json, new TypeReference<HashMap>() {});
            System.out.println(map);

            Map<String, Object> destinationMap = new HashMap<>();

            mapping(map, destinationMap, "c.d[i].five", "b.x[i].five");
            System.out.println(destinationMap);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void mapping(Map<String, Object> sourceMap, Map<String, Object> destinationMap, String from, String to) {
        String[] toTokens = to.split("\\.");
        String toFirstToken = toTokens[0];
        if ( ! toFirstToken.contains("[i]") ) {
            if ( toTokens.length > 1 ) {
                destinationMap.put(toFirstToken, new HashMap<>());
                mapping(sourceMap, (Map<String, Object>) destinationMap.get(toFirstToken), from, to.substring(to.indexOf('.') + 1));
            } else {
                destinationMap.put(toFirstToken, extractKeyFromMap(sourceMap, from));
            }
            
        } else {
            destinationMap.put(toFirstToken, new ArrayList<>());
            String sourceListKey = from.substring(0, from.indexOf("[i]"));
            List<Object> sourceList = (List<Object>) extractKeyFromMap(sourceMap, sourceListKey);
            for ( Object sourceObject : sourceList ) {
                if ( toTokens.length > 1 ) {
                    Map<String, Object> singleDestinationEntry = new HashMap<>();
                    mapping((Map<String, Object>) sourceObject, singleDestinationEntry, from.substring(from.indexOf("[i]") + 4)
                            , to.substring(to.indexOf("[i]") + 4));
                    ((List)destinationMap.get(toFirstToken)).add(singleDestinationEntry);
                } else {
                    ((List)destinationMap.get(toFirstToken)).add(sourceObject);
                }
            }
        }
    }

    public static Object extractKeyFromMap(Map map, String key) {
        if ( key.contains(".") ) {
            return extractKeyFromMap((Map) map.get(key.substring(0, key.indexOf('.'))), key.substring(key.indexOf('.') + 1));
        } else {
            return map.get(key);
        }
    }
}
