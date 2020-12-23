package ru.nsu.fit.markelov.debug;

import org.xml.sax.SAXException;
import ru.nsu.fit.markelov.xmlbeans.BrothersXML;
import ru.nsu.fit.markelov.xmlbeans.DaughtersXML;
import ru.nsu.fit.markelov.xmlbeans.PeopleXML;
import ru.nsu.fit.markelov.xmlbeans.PersonXML;
import ru.nsu.fit.markelov.xmlbeans.RefIdXML;
import ru.nsu.fit.markelov.xmlbeans.SistersXML;
import ru.nsu.fit.markelov.xmlbeans.SonsXML;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainTemp {

    private static final String SCHEMA_FILE_NAME = "src\\main\\resources\\ru\\nsu\\fit\\markelov\\people.xsd";

    public static void main(String[] args) throws JAXBException, SAXException {
        //System.out.println(System.getProperty("user.dir"));

        PersonXML personStrict1 = new PersonXML() {{
            setId("P111111");
            setName("Qwe Rty");
            setGender("Female");
        }};
        PersonXML personStrict2 = new PersonXML() {{
            setId("P222222");
            setName("Zxc Vbn");
            setGender("Male");
            setSpouse(new RefIdXML() {{setId(personStrict1);}});
//            setParents(new ParentsXML() {{setMother(new RefIdXML() {{setId(personStrict1);}});}});
//            setParents(new ParentsXML() {{setFather(new RefIdXML() {{setId(personStrict1);}});}});
            setSons(new SonsXML() {{setRefs(new ArrayList<RefIdXML>(){{add(new RefIdXML() {{setId(personStrict1);}});}});}});
            setDaughters(new DaughtersXML() {{setRefs(new ArrayList<RefIdXML>(){{add(new RefIdXML() {{setId(personStrict1);}});}});}});
            setBrothers(new BrothersXML() {{setRefs(new ArrayList<RefIdXML>(){{add(new RefIdXML() {{setId(personStrict1);}});}});}});
            setSisters(new SistersXML() {{setRefs(new ArrayList<RefIdXML>(){{add(new RefIdXML() {{setId(personStrict1);}});}});}});
        }};
            List<PersonXML> personList = new ArrayList<PersonXML>() {{
            add(personStrict1);
            add(personStrict2);
        }};
        PeopleXML people = new PeopleXML() {{
            setPersonList(personList);
        }};

        JAXBContext jc = JAXBContext.newInstance(PeopleXML.class);
        Marshaller marshaller = jc.createMarshaller();
        SchemaFactory schemaFactory = SchemaFactory.newInstance(javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI);
        marshaller.setSchema(schemaFactory.newSchema(new File(SCHEMA_FILE_NAME)));
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(people, new File("src\\main\\resources\\ru\\nsu\\fit\\markelov\\output.xml"));
    }
}
