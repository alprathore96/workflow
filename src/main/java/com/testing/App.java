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
            mapping(map, destinationMap, "b.two", "b.two");
            mapping(map, destinationMap, "a", "b.two[i].a");
            mapping(map, destinationMap, "c.d[i].six", "b.two[i].a[i]");
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
                destinationMap.putIfAbsent(toFirstToken, new HashMap<>());
                mapping(sourceMap, (Map<String, Object>) destinationMap.get(toFirstToken), from, to.substring(to.indexOf('.') + 1));
            } else {
                destinationMap.putIfAbsent(toFirstToken, extractKeyFromMap(sourceMap, from));
            }
            
        } else {
            String toFinalFirstToken = toFirstToken.substring(0, toFirstToken.indexOf("[i]"));
            destinationMap.putIfAbsent(toFinalFirstToken, new ArrayList<>());
            if ( from.contains("[i]") ) {
                String sourceListKey = from.substring(0, from.indexOf("[i]"));
                List<Object> sourceList = (List<Object>) extractKeyFromMap(sourceMap, sourceListKey);
                for (Object sourceObject : sourceList) {
                    if (toTokens.length > 1) {
                        if ( ! toTokens[1].contains("[i]")) {
                            Map<String, Object> singleDestinationEntry = new HashMap<>();
                            mapping((Map<String, Object>) sourceObject, singleDestinationEntry, from.substring(from.indexOf("[i]") + 4)
                                    , to.substring(to.indexOf("[i]") + 4));
                            ((List) destinationMap.get(toFinalFirstToken)).add(singleDestinationEntry);
                        } else {
                            
                        }
                    } else {
                        ((List) destinationMap.get(toFinalFirstToken)).add(sourceObject);
                    }
                }
            } else {
                List destinationObjects = (List) destinationMap.get(toFinalFirstToken);
                if ( toTokens.length > 1 ) {
                    for ( Object object : destinationObjects ) {
                        Map<String, Object> singleDestinationEntry = new HashMap<>();
                        if ( object instanceof Map ) {
                            singleDestinationEntry = (Map<String, Object>) object;
                        }
                        mapping(sourceMap, singleDestinationEntry, from, to.substring(to.indexOf("[i]") + 4));
                    }

                } else {
                    int size = destinationObjects.size();
                    for ( int i = 0; i < size; i++ ) {
                        destinationObjects.set(i, extractKeyFromMap(sourceMap, from));
                    }
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
