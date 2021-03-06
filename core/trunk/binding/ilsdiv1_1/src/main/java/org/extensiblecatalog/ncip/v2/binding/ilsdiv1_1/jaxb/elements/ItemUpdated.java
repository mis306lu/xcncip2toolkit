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
 *         &lt;element ref="{http://www.niso.org/2008/ncip}InitiationHeader" minOccurs="0"/>
 *         &lt;element ref="{http://www.niso.org/2008/ncip}ItemId"/>
 *         &lt;element ref="{http://www.niso.org/2008/ncip}DeleteItemFields" minOccurs="0"/>
 *         &lt;element ref="{http://www.niso.org/2008/ncip}AddItemFields" minOccurs="0"/>
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
    "initiationHeader",
    "itemId",
    "deleteItemFields",
    "addItemFields",
    "ext"
})
@XmlRootElement(name = "ItemUpdated")
public class ItemUpdated {

    @XmlElement(name = "InitiationHeader")
    protected InitiationHeader initiationHeader;
    @XmlElement(name = "ItemId", required = true)
    protected ItemId itemId;
    @XmlElement(name = "DeleteItemFields")
    protected DeleteItemFields deleteItemFields;
    @XmlElement(name = "AddItemFields")
    protected AddItemFields addItemFields;
    @XmlElement(name = "Ext")
    protected Ext ext;

    /**
     * Gets the value of the initiationHeader property.
     * 
     * @return
     *     possible object is
     *     {@link InitiationHeader }
     *     
     */
    public InitiationHeader getInitiationHeader() {
        return initiationHeader;
    }

    /**
     * Sets the value of the initiationHeader property.
     * 
     * @param value
     *     allowed object is
     *     {@link InitiationHeader }
     *     
     */
    public void setInitiationHeader(InitiationHeader value) {
        this.initiationHeader = value;
    }

    /**
     * Gets the value of the itemId property.
     * 
     * @return
     *     possible object is
     *     {@link ItemId }
     *     
     */
    public ItemId getItemId() {
        return itemId;
    }

    /**
     * Sets the value of the itemId property.
     * 
     * @param value
     *     allowed object is
     *     {@link ItemId }
     *     
     */
    public void setItemId(ItemId value) {
        this.itemId = value;
    }

    /**
     * Gets the value of the deleteItemFields property.
     * 
     * @return
     *     possible object is
     *     {@link DeleteItemFields }
     *     
     */
    public DeleteItemFields getDeleteItemFields() {
        return deleteItemFields;
    }

    /**
     * Sets the value of the deleteItemFields property.
     * 
     * @param value
     *     allowed object is
     *     {@link DeleteItemFields }
     *     
     */
    public void setDeleteItemFields(DeleteItemFields value) {
        this.deleteItemFields = value;
    }

    /**
     * Gets the value of the addItemFields property.
     * 
     * @return
     *     possible object is
     *     {@link AddItemFields }
     *     
     */
    public AddItemFields getAddItemFields() {
        return addItemFields;
    }

    /**
     * Sets the value of the addItemFields property.
     * 
     * @param value
     *     allowed object is
     *     {@link AddItemFields }
     *     
     */
    public void setAddItemFields(AddItemFields value) {
        this.addItemFields = value;
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
