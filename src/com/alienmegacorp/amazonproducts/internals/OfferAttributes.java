
package com.alienmegacorp.amazonproducts.internals;

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
 *         &lt;element name="Condition" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SubCondition" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ConditionNote" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="WillShipExpedited" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="WillShipInternational" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
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
    "condition",
    "subCondition",
    "conditionNote",
    "willShipExpedited",
    "willShipInternational"
})
@XmlRootElement(name = "OfferAttributes")
public class OfferAttributes {

    @XmlElement(name = "Condition")
    protected String condition;
    @XmlElement(name = "SubCondition")
    protected String subCondition;
    @XmlElement(name = "ConditionNote")
    protected String conditionNote;
    @XmlElement(name = "WillShipExpedited")
    protected Boolean willShipExpedited;
    @XmlElement(name = "WillShipInternational")
    protected Boolean willShipInternational;

    /**
     * Gets the value of the condition property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCondition() {
        return condition;
    }

    /**
     * Sets the value of the condition property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCondition(String value) {
        this.condition = value;
    }

    /**
     * Gets the value of the subCondition property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubCondition() {
        return subCondition;
    }

    /**
     * Sets the value of the subCondition property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubCondition(String value) {
        this.subCondition = value;
    }

    /**
     * Gets the value of the conditionNote property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConditionNote() {
        return conditionNote;
    }

    /**
     * Sets the value of the conditionNote property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConditionNote(String value) {
        this.conditionNote = value;
    }

    /**
     * Gets the value of the willShipExpedited property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isWillShipExpedited() {
        return willShipExpedited;
    }

    /**
     * Sets the value of the willShipExpedited property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setWillShipExpedited(Boolean value) {
        this.willShipExpedited = value;
    }

    /**
     * Gets the value of the willShipInternational property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isWillShipInternational() {
        return willShipInternational;
    }

    /**
     * Sets the value of the willShipInternational property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setWillShipInternational(Boolean value) {
        this.willShipInternational = value;
    }

}
