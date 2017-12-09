package com.testing.services.operations;

import com.testing.workflow.WorkflowOperation;
import com.testing.workflow.exceptions.InvalidMethodArgumentException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Alpesh Rathore on 12/9/2017.
 */
public class MappingOperation extends WorkflowOperation {

    public void mapping(Map<String, Object> sourceMap, Map<String, Object> destinationMap, String from, String to) {
        String[] toTokens = to.split("\\.");
        String toFirstToken = toTokens[0];
        if (!toFirstToken.contains("[i]")) {
            if (toTokens.length > 1) {
                destinationMap.putIfAbsent(toFirstToken, new HashMap<>());
                mapping(sourceMap, (Map<String, Object>) destinationMap.get(toFirstToken), from, to.substring(to.indexOf('.') + 1));
            } else {
                destinationMap.putIfAbsent(toFirstToken, extractKeyFromMap(sourceMap, from));
            }

        } else {
            String toFinalFirstToken = toFirstToken.substring(0, toFirstToken.indexOf("[i]"));
            destinationMap.putIfAbsent(toFinalFirstToken, new ArrayList<>());
            if ( (to.indexOf("[i]") == to.lastIndexOf("[i]")) && from.contains("[i]")) {
                String sourceListKey = from.substring(0, from.indexOf("[i]"));
                List<Object> sourceList = (List<Object>) extractKeyFromMap(sourceMap, sourceListKey);
                int i = 0;
                for (Object sourceObject : sourceList) {
                    if (toTokens.length > 1) {
                        Map<String, Object> singleDestinationEntry;
                        if ( destinationMap.get(toFinalFirstToken) != null && destinationMap.get(toFinalFirstToken) instanceof List
                                && ((List)destinationMap.get(toFinalFirstToken)).get(i) instanceof Map ) {
                            singleDestinationEntry = ((Map) ((List)destinationMap.get(toFinalFirstToken)).get(i));
                            mapping((Map<String, Object>) sourceObject, singleDestinationEntry, from.substring(from.indexOf("[i]") + 4)
                                    , to.substring(to.indexOf("[i]") + 4));
                        } else {
                            singleDestinationEntry = new HashMap<>();
                            mapping((Map<String, Object>) sourceObject, singleDestinationEntry, from.substring(from.indexOf("[i]") + 4)
                                    , to.substring(to.indexOf("[i]") + 4));
                            ((List)destinationMap.get(toFinalFirstToken)).add(singleDestinationEntry);
                        }
                    } else {
                        ((List) destinationMap.get(toFinalFirstToken)).add(extractKeyFromMap((Map)sourceObject, from.substring(from.indexOf("[i]") + 4)));
                    }
                    i++;
                }
            } else {
                List destinationObjects = (List) destinationMap.get(toFinalFirstToken);
                if (toTokens.length > 1) {
                    for (Object object : destinationObjects) {
                        Map<String, Object> singleDestinationEntry = new HashMap<>();
                        if (object instanceof Map) {
                            singleDestinationEntry = (Map<String, Object>) object;
                        }
                        mapping(sourceMap, singleDestinationEntry, from, to.substring(to.indexOf("[i]") + 4));
                    }
                } else {
                    int size = destinationObjects.size();
                    for (int i = 0; i < size; i++) {
                        destinationObjects.set(i, extractKeyFromMap(sourceMap, from));
                    }
                }
            }
        }
    }

    public static Object extractKeyFromMap(Map map, String key) {
        if (key.contains(".")) {
            return extractKeyFromMap((Map) map.get(key.substring(0, key.indexOf('.'))), key.substring(key.indexOf('.') + 1));
        } else {
            return map.get(key);
        }
    }

    @Override
    public Object operate(Map params) throws InvalidMethodArgumentException {
        return null;
    }
}
