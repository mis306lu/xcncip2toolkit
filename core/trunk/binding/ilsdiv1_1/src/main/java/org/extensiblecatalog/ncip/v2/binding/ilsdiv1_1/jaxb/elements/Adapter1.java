//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-b10 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.06.11 at 02:59:27 PM CEST 
//


package org.extensiblecatalog.ncip.v2.binding.ilsdiv1_1.jaxb.elements;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.datatype.XMLGregorianCalendar;

public class Adapter1
    extends XmlAdapter<String, XMLGregorianCalendar>
{


    public XMLGregorianCalendar unmarshal(String value) {
        return (org.extensiblecatalog.ncip.v2.binding.jaxb.DatatypeConverter.parseDateTime(value));
    }

    public String marshal(XMLGregorianCalendar value) {
        return (org.extensiblecatalog.ncip.v2.binding.jaxb.DatatypeConverter.printDateTime(value));
    }

}
