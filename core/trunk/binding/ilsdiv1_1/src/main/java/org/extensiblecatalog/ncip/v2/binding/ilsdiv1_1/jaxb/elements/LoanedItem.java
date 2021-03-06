//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-b10 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.06.11 at 02:59:27 PM CEST 
//


package org.extensiblecatalog.ncip.v2.binding.ilsdiv1_1.jaxb.elements;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;


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
 *         &lt;element ref="{http://www.niso.org/2008/ncip}ItemId"/>
 *         &lt;element ref="{http://www.niso.org/2008/ncip}BibliographicId" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://www.niso.org/2008/ncip}ReminderLevel" minOccurs="0"/>
 *         &lt;choice>
 *           &lt;element ref="{http://www.niso.org/2008/ncip}DateDue"/>
 *           &lt;element ref="{http://www.niso.org/2008/ncip}IndeterminateLoanPeriodFlag"/>
 *         &lt;/choice>
 *         &lt;element ref="{http://www.niso.org/2008/ncip}Amount" minOccurs="0"/>
 *         &lt;element ref="{http://www.niso.org/2008/ncip}Title" minOccurs="0"/>
 *         &lt;element ref="{http://www.niso.org/2008/ncip}MediumType" minOccurs="0"/>
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
    "itemId",
    "bibliographicId",
    "reminderLevel",
    "dateDue",
    "indeterminateLoanPeriodFlag",
    "amount",
    "title",
    "mediumType",
    "ext"
})
@XmlRootElement(name = "LoanedItem")
public class LoanedItem {

    @XmlElement(name = "ItemId", required = true)
    protected ItemId itemId;
    @XmlElement(name = "BibliographicId")
    protected List<BibliographicId> bibliographicId;
    @XmlElement(name = "ReminderLevel", type = String.class)
    @XmlJavaTypeAdapter(Adapter2 .class)
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigDecimal reminderLevel;
    @XmlElement(name = "DateDue", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateDue;
    @XmlElement(name = "IndeterminateLoanPeriodFlag")
    protected IndeterminateLoanPeriodFlag indeterminateLoanPeriodFlag;
    @XmlElement(name = "Amount")
    protected Amount amount;
    @XmlElement(name = "Title")
    protected String title;
    @XmlElement(name = "MediumType")
    protected SchemeValuePair mediumType;
    @XmlElement(name = "Ext")
    protected Ext ext;

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
     * Gets the value of the bibliographicId property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the bibliographicId property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBibliographicId().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BibliographicId }
     * 
     * 
     */
    public List<BibliographicId> getBibliographicId() {
        if (bibliographicId == null) {
            bibliographicId = new ArrayList<BibliographicId>();
        }
        return this.bibliographicId;
    }

    /**
     * Gets the value of the reminderLevel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public BigDecimal getReminderLevel() {
        return reminderLevel;
    }

    /**
     * Sets the value of the reminderLevel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReminderLevel(BigDecimal value) {
        this.reminderLevel = value;
    }

    /**
     * Gets the value of the dateDue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public XMLGregorianCalendar getDateDue() {
        return dateDue;
    }

    /**
     * Sets the value of the dateDue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDateDue(XMLGregorianCalendar value) {
        this.dateDue = value;
    }

    /**
     * Gets the value of the indeterminateLoanPeriodFlag property.
     * 
     * @return
     *     possible object is
     *     {@link IndeterminateLoanPeriodFlag }
     *     
     */
    public IndeterminateLoanPeriodFlag getIndeterminateLoanPeriodFlag() {
        return indeterminateLoanPeriodFlag;
    }

    /**
     * Sets the value of the indeterminateLoanPeriodFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link IndeterminateLoanPeriodFlag }
     *     
     */
    public void setIndeterminateLoanPeriodFlag(IndeterminateLoanPeriodFlag value) {
        this.indeterminateLoanPeriodFlag = value;
    }

    /**
     * Gets the value of the amount property.
     * 
     * @return
     *     possible object is
     *     {@link Amount }
     *     
     */
    public Amount getAmount() {
        return amount;
    }

    /**
     * Sets the value of the amount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Amount }
     *     
     */
    public void setAmount(Amount value) {
        this.amount = value;
    }

    /**
     * Gets the value of the title property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the value of the title property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitle(String value) {
        this.title = value;
    }

    /**
     * Gets the value of the mediumType property.
     * 
     * @return
     *     possible object is
     *     {@link SchemeValuePair }
     *     
     */
    public SchemeValuePair getMediumType() {
        return mediumType;
    }

    /**
     * Sets the value of the mediumType property.
     * 
     * @param value
     *     allowed object is
     *     {@link SchemeValuePair }
     *     
     */
    public void setMediumType(SchemeValuePair value) {
        this.mediumType = value;
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
