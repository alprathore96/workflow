package com.testing.operations.XmlOperations;

import com.testing.services.converters.Xml2Json;
import com.testing.workflow.WorkflowOperation;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

public class XmlToJsonOperation extends WorkflowOperation {

    public String convert(String xml) {
        try {
            xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                    "<parent id=\"5\">\n" +
                    "<child1>1</child1>\n" +
                    "<child2>2</child2>\n" +
                    "<child2>3</child2>\n" +
                    "</parent>";
            String json = Xml2Json.convert(xml);
//            if ( json == null ) {
//                throw new WorkflowExecutionException("Could not convert to json");
//            }
            System.out.println(json);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        return null;
    }

}
