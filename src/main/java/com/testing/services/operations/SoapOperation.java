package com.testing.services.operations;

import com.testing.workflow.WorkflowOperation;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
@Scope("prototype")
public class SoapOperation extends WorkflowOperation {

    private static final Logger LOGGER = Logger.getLogger(SoapOperation.class);

    public String sendMessageToSOAPServer(SOAPConnection soapConnection, String uriSOAPServer, String xmlRequestBody,
                                          String operation, String prefixNamespace, String namespace) throws SOAPException, SAXException, IOException,
            ParserConfigurationException {

        // Send SOAP Message to SOAP Server
        final SOAPElement stringToSOAPElement = stringToSOAPElement(xmlRequestBody);
        final SOAPMessage soapResponse = soapConnection.call(
                createSOAPRequest(stringToSOAPElement, operation, prefixNamespace, namespace, uriSOAPServer),
                uriSOAPServer);

        // Print SOAP Response
        LOGGER.info("Response SOAP Message : " + soapResponse.toString());
        this.setOperationAttribute("soap_result", soapResponse);
        return soapResponse.toString();
    }

    /**
     * Create a SOAP connection
     *
     * @throws UnsupportedOperationException
     * @throws SOAPException
     */
    private SOAPConnection createSOAPConnection() throws UnsupportedOperationException,
            SOAPException {

        // Create SOAP Connection
        SOAPConnectionFactory soapConnectionFactory;
        soapConnectionFactory = SOAPConnectionFactory.newInstance();
        return soapConnectionFactory.createConnection();
    }

    /**
     * Create a SOAP request
     *
     * @param body
     *            the body of the SOAP message
     * @param operation
     *            the operation from the SOAP server invoked
     * @return the SOAP message request completed
     * @throws SOAPException
     */
    private SOAPMessage createSOAPRequest(SOAPElement body, String operation, String prefixNamespace, String namespace
    , String uriSOAPServer)
            throws SOAPException {

        final MessageFactory messageFactory = MessageFactory.newInstance();
        final SOAPMessage soapMessage = messageFactory.createMessage();
        final SOAPPart soapPart = soapMessage.getSOAPPart();

        // SOAP Envelope
        final SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration(prefixNamespace, namespace);

        // SOAP Body
        final SOAPBody soapBody = envelope.getBody();
        soapBody.addChildElement(body);

        // Mime Headers
        final MimeHeaders headers = soapMessage.getMimeHeaders();
        LOGGER.info("SOAPAction : " + uriSOAPServer + operation);
        headers.addHeader("SOAPAction", uriSOAPServer + operation);

        soapMessage.saveChanges();

		/* Print the request message */
        LOGGER.info("Request SOAP Message :" + soapMessage.toString());
        return soapMessage;
    }

    /**
     * Transform a String to a SOAP element
     *
     * @param xmlRequestBody
     *            the string body representation
     * @return a SOAP element
     * @throws SOAPException
     * @throws SAXException
     * @throws IOException
     * @throws ParserConfigurationException
     */
    private SOAPElement stringToSOAPElement(String xmlRequestBody)
            throws SOAPException, SAXException, IOException,
            ParserConfigurationException {

        // Load the XML text into a DOM Document
        final DocumentBuilderFactory builderFactory = DocumentBuilderFactory
                .newInstance();
        builderFactory.setNamespaceAware(true);
        final InputStream stream = new ByteArrayInputStream(
                xmlRequestBody.getBytes());
        final Document doc = builderFactory.newDocumentBuilder().parse(stream);

        // Use SAAJ to convert Document to SOAPElement
        // Create SoapMessage
        final MessageFactory msgFactory = MessageFactory.newInstance();
        final SOAPMessage message = msgFactory.createMessage();
        final SOAPBody soapBody = message.getSOAPBody();

        // This returns the SOAPBodyElement that contains ONLY the Payload
        return soapBody.addDocument(doc);
    }
}
