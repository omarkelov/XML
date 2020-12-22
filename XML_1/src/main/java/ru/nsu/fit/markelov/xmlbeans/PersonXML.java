package ru.nsu.fit.markelov.xmlbeans;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "person")
@XmlType(propOrder = { "id", "name", "gender", "spouse", "parents", "sons", "daughters", "brothers", "sisters" })
public class PersonXML {

    String id;
    String name;
    String gender;
    RefIdXML spouse;
    ParentsXML parents;
    SonsXML sons;
    DaughtersXML daughters;
    BrothersXML brothers;
    SistersXML sisters;

    @XmlID
    @XmlAttribute(name = "id", required = true)
    public void setId(String id) {
        this.id = id;
    }

    @XmlAttribute(name = "name", required = true)
    public void setName(String name) {
        this.name = name;
    }

    @XmlAttribute(name = "gender", required = true)
    public void setGender(String gender) {
        this.gender = gender;
    }

    @XmlElement(name = "spouse")
    public void setSpouse(RefIdXML ref) {
        this.spouse = ref;
    }

    @XmlElement(name = "parents")
    public void setParents(ParentsXML parents) {
        this.parents = parents;
    }

    @XmlElement(name = "sons")
    public void setSons(SonsXML sons) {
        this.sons = sons;
    }

    @XmlElement(name = "daughters")
    public void setDaughters(DaughtersXML daughters) {
        this.daughters = daughters;
    }

    @XmlElement(name = "brothers")
    public void setBrothers(BrothersXML brothers) {
        this.brothers = brothers;
    }

    @XmlElement(name = "sisters")
    public void setSisters(SistersXML sisters) {
        this.sisters = sisters;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public RefIdXML getSpouse() {
        return spouse;
    }

    public ParentsXML getParents() {
        return parents;
    }

    public SonsXML getSons() {
        return sons;
    }

    public DaughtersXML getDaughters() {
        return daughters;
    }

    public BrothersXML getBrothers() {
        return brothers;
    }

    public SistersXML getSisters() {
        return sisters;
    }
}
