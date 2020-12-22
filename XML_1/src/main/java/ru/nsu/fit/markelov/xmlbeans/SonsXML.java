package ru.nsu.fit.markelov.xmlbeans;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlType(name = "sons-type")
public class SonsXML {

    private List<RefIdXML> refs;

    @XmlElement(name = "son")
    public void setRefs(List<RefIdXML> refs) {
        this.refs = refs;
    }

    public List<RefIdXML> getRefs() {
        return refs;
    }
}
