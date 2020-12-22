package ru.nsu.fit.markelov.xmlbeans;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "parents-type", propOrder = { "father", "mother" })
public class ParentsXML {

    private RefIdXML father;
    private RefIdXML mother;

    @XmlElement(name = "father")
    public void setFather(RefIdXML father) {
        this.father = father;
    }

    @XmlElement(name = "mother")
    public void setMother(RefIdXML mother) {
        this.mother = mother;
    }

    public RefIdXML getFather() {
        return father;
    }

    public RefIdXML getMother() {
        return mother;
    }
}
