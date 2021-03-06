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
 *         &lt;element ref="{http://www.niso.org/2008/ncip}UserId" minOccurs="0"/>
 *         &lt;element ref="{http://www.niso.org/2008/ncip}ItemId" minOccurs="0"/>
 *         &lt;element ref="{http://www.niso.org/2008/ncip}RequestType" minOccurs="0"/>
 *         &lt;element ref="{http://www.niso.org/2008/ncip}RequestScopeType" minOccurs="0"/>
 *         &lt;element ref="{http://www.niso.org/2008/ncip}RequestStatusType" minOccurs="0"/>
 *         &lt;element ref="{http://www.niso.org/2008/ncip}ShippingInformation" minOccurs="0"/>
 *         &lt;element ref="{http://www.niso.org/2008/ncip}EarliestDateNeeded" minOccurs="0"/>
 *         &lt;element ref="{http://www.niso.org/2008/ncip}NeedBeforeDate" minOccurs="0"/>
 *         &lt;element ref="{http://www.niso.org/2008/ncip}PickupLocation" minOccurs="0"/>
 *         &lt;element ref="{http://www.niso.org/2008/ncip}PickupExpiryDate" minOccurs="0"/>
 *         &lt;element ref="{http://www.niso.org/2008/ncip}DateOfUserRequest" minOccurs="0"/>
 *         &lt;element ref="{http://www.niso.org/2008/ncip}DateAvailable" minOccurs="0"/>
 *         &lt;element ref="{http://www.niso.org/2008/ncip}AcknowledgedFeeAmount" minOccurs="0"/>
 *         &lt;element ref="{http://www.niso.org/2008/ncip}PaidFeeAmount" minOccurs="0"/>
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
    "userId",
    "itemId",
    "requestType",
    "requestScopeType",
    "requestStatusType",
    "shippingInformation",
    "earliestDateNeeded",
    "needBeforeDate",
    "pickupLocation",
    "pickupExpiryDate",
    "dateOfUserRequest",
    "dateAvailable",
    "acknowledgedFeeAmount",
    "paidFeeAmount",
    "ext"
})
@XmlRootElement(name = "DeleteRequestFields")
public class DeleteRequestFields {

    @XmlElement(name = "UserId")
    protected UserId userId;
    @XmlElement(name = "ItemId")
    protected ItemId itemId;
    @XmlElement(name = "RequestType")
    protected SchemeValuePair requestType;
    @XmlElement(name = "RequestScopeType")
    protected SchemeValuePair requestScopeType;
    @XmlElement(name = "RequestStatusType")
    protected SchemeValuePair requestStatusType;
    @XmlElement(name = "ShippingInformation")
    protected ShippingInformation shippingInformation;
    @XmlElement(name = "EarliestDateNeeded", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar earliestDateNeeded;
    @XmlElement(name = "NeedBeforeDate", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar needBeforeDate;
    @XmlElement(name = "PickupLocation")
    protected SchemeValuePair pickupLocation;
    @XmlElement(name = "PickupExpiryDate", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar pickupExpiryDate;
    @XmlElement(name = "DateOfUserRequest", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateOfUserRequest;
    @XmlElement(name = "DateAvailable", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateAvailable;
    @XmlElement(name = "AcknowledgedFeeAmount")
    protected AcknowledgedFeeAmount acknowledgedFeeAmount;
    @XmlElement(name = "PaidFeeAmount")
    protected PaidFeeAmount paidFeeAmount;
    @XmlElement(name = "Ext")
    protected Ext ext;

    /**
     * Gets the value of the userId property.
     * 
     * @return
     *     possible object is
     *     {@link UserId }
     *     
     */
    public UserId getUserId() {
        return userId;
    }

    /**
     * Sets the value of the userId property.
     * 
     * @param value
     *     allowed object is
     *     {@link UserId }
     *     
     */
    public void setUserId(UserId value) {
        this.userId = value;
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
     * Gets the value of the requestType property.
     * 
     * @return
     *     possible object is
     *     {@link SchemeValuePair }
     *     
     */
    public SchemeValuePair getRequestType() {
        return requestType;
    }

    /**
     * Sets the value of the requestType property.
     * 
     * @param value
     *     allowed object is
     *     {@link SchemeValuePair }
     *     
     */
    public void setRequestType(SchemeValuePair value) {
        this.requestType = value;
    }

    /**
     * Gets the value of the requestScopeType property.
     * 
     * @return
     *     possible object is
     *     {@link SchemeValuePair }
     *     
     */
    public SchemeValuePair getRequestScopeType() {
        return requestScopeType;
    }

    /**
     * Sets the value of the requestScopeType property.
     * 
     * @param value
     *     allowed object is
     *     {@link SchemeValuePair }
     *     
     */
    public void setRequestScopeType(SchemeValuePair value) {
        this.requestScopeType = value;
    }

    /**
     * Gets the value of the requestStatusType property.
     * 
     * @return
     *     possible object is
     *     {@link SchemeValuePair }
     *     
     */
    public SchemeValuePair getRequestStatusType() {
        return requestStatusType;
    }

    /**
     * Sets the value of the requestStatusType property.
     * 
     * @param value
     *     allowed object is
     *     {@link SchemeValuePair }
     *     
     */
    public void setRequestStatusType(SchemeValuePair value) {
        this.requestStatusType = value;
    }

    /**
     * Gets the value of the shippingInformation property.
     * 
     * @return
     *     possible object is
     *     {@link ShippingInformation }
     *     
     */
    public ShippingInformation getShippingInformation() {
        return shippingInformation;
    }

    /**
     * Sets the value of the shippingInformation property.
     * 
     * @param value
     *     allowed object is
     *     {@link ShippingInformation }
     *     
     */
    public void setShippingInformation(ShippingInformation value) {
        this.shippingInformation = value;
    }

    /**
     * Gets the value of the earliestDateNeeded property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public XMLGregorianCalendar getEarliestDateNeeded() {
        return earliestDateNeeded;
    }

    /**
     * Sets the value of the earliestDateNeeded property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEarliestDateNeeded(XMLGregorianCalendar value) {
        this.earliestDateNeeded = value;
    }

    /**
     * Gets the value of the needBeforeDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public XMLGregorianCalendar getNeedBeforeDate() {
        return needBeforeDate;
    }

    /**
     * Sets the value of the needBeforeDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNeedBeforeDate(XMLGregorianCalendar value) {
        this.needBeforeDate = value;
    }

    /**
     * Gets the value of the pickupLocation property.
     * 
     * @return
     *     possible object is
     *     {@link SchemeValuePair }
     *     
     */
    public SchemeValuePair getPickupLocation() {
        return pickupLocation;
    }

    /**
     * Sets the value of the pickupLocation property.
     * 
     * @param value
     *     allowed object is
     *     {@link SchemeValuePair }
     *     
     */
    public void setPickupLocation(SchemeValuePair value) {
        this.pickupLocation = value;
    }

    /**
     * Gets the value of the pickupExpiryDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public XMLGregorianCalendar getPickupExpiryDate() {
        return pickupExpiryDate;
    }

    /**
     * Sets the value of the pickupExpiryDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPickupExpiryDate(XMLGregorianCalendar value) {
        this.pickupExpiryDate = value;
    }

    /**
     * Gets the value of the dateOfUserRequest property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public XMLGregorianCalendar getDateOfUserRequest() {
        return dateOfUserRequest;
    }

    /**
     * Sets the value of the dateOfUserRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDateOfUserRequest(XMLGregorianCalendar value) {
        this.dateOfUserRequest = value;
    }

    /**
     * Gets the value of the dateAvailable property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public XMLGregorianCalendar getDateAvailable() {
        return dateAvailable;
    }

    /**
     * Sets the value of the dateAvailable property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDateAvailable(XMLGregorianCalendar value) {
        this.dateAvailable = value;
    }

    /**
     * Gets the value of the acknowledgedFeeAmount property.
     * 
     * @return
     *     possible object is
     *     {@link AcknowledgedFeeAmount }
     *     
     */
    public AcknowledgedFeeAmount getAcknowledgedFeeAmount() {
        return acknowledgedFeeAmount;
    }

    /**
     * Sets the value of the acknowledgedFeeAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link AcknowledgedFeeAmount }
     *     
     */
    public void setAcknowledgedFeeAmount(AcknowledgedFeeAmount value) {
        this.acknowledgedFeeAmount = value;
    }

    /**
     * Gets the value of the paidFeeAmount property.
     * 
     * @return
     *     possible object is
     *     {@link PaidFeeAmount }
     *     
     */
    public PaidFeeAmount getPaidFeeAmount() {
        return paidFeeAmount;
    }

    /**
     * Sets the value of the paidFeeAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link PaidFeeAmount }
     *     
     */
    public void setPaidFeeAmount(PaidFeeAmount value) {
        this.paidFeeAmount = value;
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
