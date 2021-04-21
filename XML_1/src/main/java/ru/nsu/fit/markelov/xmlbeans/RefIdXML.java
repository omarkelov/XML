package ru.nsu.fit.markelov.xmlbeans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ref-type")
public class RefIdXML {

    @XmlAttribute(name = "id", required = true)
    @XmlIDREF
    @XmlSchemaType(name = "IDREF")
    Object id;

    public void setId(Object id) {
        this.id = id;
    }

    public Object getId() {
        return id;
    }
}
