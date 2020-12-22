package ru.nsu.fit.markelov;

import org.xml.sax.SAXException;
import ru.nsu.fit.markelov.xmlbeans.BrothersXML;
import ru.nsu.fit.markelov.xmlbeans.DaughtersXML;
import ru.nsu.fit.markelov.xmlbeans.ParentsXML;
import ru.nsu.fit.markelov.xmlbeans.PeopleXML;
import ru.nsu.fit.markelov.xmlbeans.PersonXML;
import ru.nsu.fit.markelov.xmlbeans.RefIdXML;
import ru.nsu.fit.markelov.xmlbeans.SistersXML;
import ru.nsu.fit.markelov.xmlbeans.SonsXML;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Marshaller {

    private static final String SCHEMA_FILE_NAME = "src\\main\\resources\\ru\\nsu\\fit\\markelov\\people.xsd";
    private static final String OUTPUT_FILE_NAME = "src\\main\\resources\\ru\\nsu\\fit\\markelov\\people_strict.xml";

    private final Collection<Person> persons;
    private final Map<String, PersonXML> personXmlMap = new HashMap<>();

    public Marshaller(Collection<Person> persons) throws JAXBException, SAXException {
        this.persons = persons;

        fillMap();

        PeopleXML people = new PeopleXML() {{
            setPersonList(new ArrayList<>(personXmlMap.values()));
        }};

        marshal(people);
    }

    private void fillMap() {
        for (Person person : persons) {
            personXmlMap.put(person.getId(), new PersonXML());
        }

        for (Person person : persons) {
            PersonXML personXML = personXmlMap.get(person.getId());

            personXML.setId(person.getId());
            personXML.setName(person.getFullName());
            personXML.setGender(person.getGender());
            if (person.getHusbandId() != null) {
                personXML.setSpouse(new RefIdXML() {{
                    setId(personXmlMap.get(person.getHusbandId()));
                }});
            }
            if (person.getWifeId() != null) {
                personXML.setSpouse(new RefIdXML() {{
                    setId(personXmlMap.get(person.getWifeId()));
                }});
            }
            if (person.getFatherId() != null) {
                ParentsXML parentsXML = personXML.getParents();
                if (parentsXML == null) {
                    parentsXML = new ParentsXML();
                    personXML.setParents(parentsXML);
                }
                parentsXML.setFather(new RefIdXML() {{
                    setId(personXmlMap.get(person.getFatherId()));
                }});
            }
            if (person.getMotherId() != null) {
                ParentsXML parentsXML = personXML.getParents();
                if (parentsXML == null) {
                    parentsXML = new ParentsXML();
                    personXML.setParents(parentsXML);
                }
                parentsXML.setMother(new RefIdXML() {{
                    setId(personXmlMap.get(person.getMotherId()));
                }});
            }
            if (!person.getSonIdSet().isEmpty()) {
                personXML.setSons(new SonsXML() {{
                    List<RefIdXML> sonsXml = new ArrayList<>();
                    for (String sonId : person.getSonIdSet()) {
                        sonsXml.add(new RefIdXML() {{
                            setId(personXmlMap.get(sonId));
                        }});
                    }
                    setRefs(sonsXml);
                }});
            }
            if (!person.getDaughterIdSet().isEmpty()) {
                personXML.setDaughters(new DaughtersXML() {{
                    List<RefIdXML> daughtersXml = new ArrayList<>();
                    for (String daughterId : person.getDaughterIdSet()) {
                        daughtersXml.add(new RefIdXML() {{
                            setId(personXmlMap.get(daughterId));
                        }});
                    }
                    setRefs(daughtersXml);
                }});
            }
            if (!person.getBrotherIdSet().isEmpty()) {
                personXML.setBrothers(new BrothersXML() {{
                    List<RefIdXML> brothersXML = new ArrayList<>();
                    for (String brotherId : person.getBrotherIdSet()) {
                        brothersXML.add(new RefIdXML() {{
                            setId(personXmlMap.get(brotherId));
                        }});
                    }
                    setRefs(brothersXML);
                }});
            }
            if (!person.getSisterIdSet().isEmpty()) {
                personXML.setSisters(new SistersXML() {{
                    List<RefIdXML> sistersXML = new ArrayList<>();
                    for (String sisterId : person.getSisterIdSet()) {
                        sistersXML.add(new RefIdXML() {{
                            setId(personXmlMap.get(sisterId));
                        }});
                    }
                    setRefs(sistersXML);
                }});
            }
        }
    }

    private void marshal(PeopleXML people) throws JAXBException, SAXException {
        JAXBContext jc = JAXBContext.newInstance(PeopleXML.class);
        javax.xml.bind.Marshaller marshaller = jc.createMarshaller();
        SchemaFactory schemaFactory = SchemaFactory.newInstance(javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI);
        marshaller.setSchema(schemaFactory.newSchema(new File(SCHEMA_FILE_NAME)));
        marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(people, new File(OUTPUT_FILE_NAME));
    }
}
