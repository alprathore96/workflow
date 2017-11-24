package com.testing.operations.testWfOperations;

import com.github.fge.jsonpatch.JsonPatchException;
import com.testing.services.JsonTransformer;
import com.testing.services.factories.MappingFactory;
import com.testing.workflow.WorkflowOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Scope("prototype")
public class JsonTransformFilter extends WorkflowOperation {

    private static final Logger LOGGER = Logger.getLogger(JsonTransformFilter.class);
    @Autowired
    JsonTransformer jsonTransformer;

    public Object transform(String sourceJson) {
        
        String mappingForWorkflow = MappingFactory.getMappingForWorkflow(this.getWorkflowContext().getWorkflow().getId());
        try {
            this.setOperationAttribute("transformed", sourceJson);
            return jsonTransformer.transform(sourceJson, mappingForWorkflow);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JsonPatchException e) {
            e.printStackTrace();
        }
        return null;
    }

}
