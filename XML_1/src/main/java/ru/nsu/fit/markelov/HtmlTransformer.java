package ru.nsu.fit.markelov;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class HtmlTransformer {

    private static final String SCHEMA_FILE_NAME = "src\\main\\resources\\ru\\nsu\\fit\\markelov\\people.xsd";
    private static final String DATA_FILE_NAME = "src\\main\\resources\\ru\\nsu\\fit\\markelov\\people_strict.xml";
    private static final String TRANSFORM_FILE_NAME = "src\\main\\resources\\ru\\nsu\\fit\\markelov\\transform.xsl";
    private static final String HTML_FILE_NAME = "src\\main\\resources\\ru\\nsu\\fit\\markelov\\people.html";

    public HtmlTransformer() throws SAXException, ParserConfigurationException, TransformerException, IOException {
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        SchemaFactory schemaFactory= SchemaFactory.newInstance(javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI);
        File schemaFile = new File(SCHEMA_FILE_NAME);
        docBuilderFactory.setSchema(schemaFactory.newSchema(schemaFile));
        docBuilderFactory.setNamespaceAware(true);
        Document doc = docBuilderFactory.newDocumentBuilder().parse(DATA_FILE_NAME);
        StreamSource styleSheet = new StreamSource(new File(TRANSFORM_FILE_NAME));
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer(styleSheet);
        transformer.transform(new DOMSource(doc), new StreamResult(new FileOutputStream(HTML_FILE_NAME)));

        System.out.println("Transformed to html");
    }
}
