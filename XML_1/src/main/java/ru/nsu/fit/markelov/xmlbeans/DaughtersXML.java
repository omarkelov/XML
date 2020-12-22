package ru.nsu.fit.markelov.xmlbeans;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlType(name = "daughters-type")
public class DaughtersXML {

    private List<RefIdXML> refs;

    @XmlElement(name = "daughter")
    public void setRefs(List<RefIdXML> refs) {
        this.refs = refs;
    }

    public List<RefIdXML> getRefs() {
        return refs;
    }
}
