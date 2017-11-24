package com.testing.services.converters;

import org.apache.log4j.Logger;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class Xml2Json {

    private static final Logger LOGGER = Logger.getLogger(Xml2Json.class);

    public static String convert(String xml) throws IOException, XMLStreamException {
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        InputStream in = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8.name()));
        XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
        return null;
    }

}
