//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-b10 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.06.11 at 02:59:27 PM CEST 
//


package org.extensiblecatalog.ncip.v2.binding.ilsdiv1_1.jaxb.elements;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.niso.org/2008/ncip}ShippingInstructions" minOccurs="0"/>
 *         &lt;element ref="{http://www.niso.org/2008/ncip}ShippingNote" minOccurs="0"/>
 *         &lt;choice>
 *           &lt;element ref="{http://www.niso.org/2008/ncip}PhysicalAddress"/>
 *           &lt;element ref="{http://www.niso.org/2008/ncip}ElectronicAddress"/>
 *         &lt;/choice>
 *         &lt;element ref="{http://www.niso.org/2008/ncip}Ext" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "shippingInstructions",
    "shippingNote",
    "physicalAddress",
    "electronicAddress",
    "ext"
})
@XmlRootElement(name = "ShippingInformation")
public class ShippingInformation {

    @XmlElement(name = "ShippingInstructions")
    protected String shippingInstructions;
    @XmlElement(name = "ShippingNote")
    protected String shippingNote;
    @XmlElement(name = "PhysicalAddress")
    protected PhysicalAddress physicalAddress;
    @XmlElement(name = "ElectronicAddress")
    protected ElectronicAddress electronicAddress;
    @XmlElement(name = "Ext")
    protected Ext ext;

    /**
     * Gets the value of the shippingInstructions property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShippingInstructions() {
        return shippingInstructions;
    }

    /**
     * Sets the value of the shippingInstructions property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShippingInstructions(String value) {
        this.shippingInstructions = value;
    }

    /**
     * Gets the value of the shippingNote property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShippingNote() {
        return shippingNote;
    }

    /**
     * Sets the value of the shippingNote property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShippingNote(String value) {
        this.shippingNote = value;
    }

    /**
     * Gets the value of the physicalAddress property.
     * 
     * @return
     *     possible object is
     *     {@link PhysicalAddress }
     *     
     */
    public PhysicalAddress getPhysicalAddress() {
        return physicalAddress;
    }

    /**
     * Sets the value of the physicalAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link PhysicalAddress }
     *     
     */
    public void setPhysicalAddress(PhysicalAddress value) {
        this.physicalAddress = value;
    }

    /**
     * Gets the value of the electronicAddress property.
     * 
     * @return
     *     possible object is
     *     {@link ElectronicAddress }
     *     
     */
    public ElectronicAddress getElectronicAddress() {
        return electronicAddress;
    }

    /**
     * Sets the value of the electronicAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link ElectronicAddress }
     *     
     */
    public void setElectronicAddress(ElectronicAddress value) {
        this.electronicAddress = value;
    }

    /**
     * Gets the value of the ext property.
     * 
     * @return
     *     possible object is
     *     {@link Ext }
     *     
     */
    public Ext getExt() {
        return ext;
    }

    /**
     * Sets the value of the ext property.
     * 
     * @param value
     *     allowed object is
     *     {@link Ext }
     *     
     */
    public void setExt(Ext value) {
        this.ext = value;
    }

}
