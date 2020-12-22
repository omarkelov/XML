package ru.nsu.fit.markelov.xmlbeans;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlType(name = "brothers-type")
public class BrothersXML {

    private List<RefIdXML> refs;

    @XmlElement(name = "brother")
    public void setRefs(List<RefIdXML> refs) {
        this.refs = refs;
    }

    public List<RefIdXML> getRefs() {
        return refs;
    }
}
