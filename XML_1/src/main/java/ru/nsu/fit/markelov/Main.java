package ru.nsu.fit.markelov;

import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.Collection;

public class Main {
    public static void main(String[] args) throws JAXBException, SAXException,
        IOException, TransformerException, ParserConfigurationException
    {
        Collection<Person> persons = new Parser().getIdMap();

        new Marshaller(persons);

        new HtmlTransformer();
    }
}
