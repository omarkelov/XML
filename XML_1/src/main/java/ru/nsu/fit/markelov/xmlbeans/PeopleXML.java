package ru.nsu.fit.markelov.xmlbeans;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "people", namespace = "http://fit.nsu.ru/people")
public class PeopleXML {

    private List<PersonXML> personList;

    @XmlElement(name = "person")
    public void setPersonList(List<PersonXML> personList) {
        this.personList = personList;
    }

    public List<PersonXML> getPersonList() {
        return personList;
    }
}
