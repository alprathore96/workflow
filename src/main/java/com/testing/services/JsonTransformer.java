package com.testing.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class JsonTransformer {

    private static final Logger LOGGER = Logger.getLogger(JsonTransformer.class);

    public JsonNode transform(String sourceJson, String patch) throws IOException, JsonPatchException {
        if ( sourceJson == null || patch == null ) {
            return null;
        }
        final ObjectMapper mapper = new ObjectMapper();
        final JsonPatch jsonPatch = mapper.readValue(patch, JsonPatch.class);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = objectMapper.readTree(sourceJson);
        return jsonPatch.apply(node);
    }
}
