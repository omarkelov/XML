package ru.nsu.fit.markelov.xmlbeans;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlType(name = "sisters-type")
public class SistersXML {

    private List<RefIdXML> refs;

    @XmlElement(name = "sister")
    public void setRefs(List<RefIdXML> refs) {
        this.refs = refs;
    }

    public List<RefIdXML> getRefs() {
        return refs;
    }
}
