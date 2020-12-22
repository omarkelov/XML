package ru.nsu.fit.markelov;

import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import java.util.Collection;

public class Main {
    public static void main(String[] args) throws JAXBException, SAXException {
        Collection<Person> persons = new Parser().getIdMap();

        new Marshaller(persons);
    }
}
