
package com.alienmegacorp.amazonproducts.internals;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PromotionEligibilityRequirement complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PromotionEligibilityRequirement">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="EligibilityRequirementType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Quantity" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="CurrencyAmount" type="{http://webservices.amazon.com/AWSECommerceService/2010-04-01}Price" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PromotionEligibilityRequirement", propOrder = {
    "eligibilityRequirementType",
    "quantity",
    "currencyAmount"
})
public class PromotionEligibilityRequirement {

    @XmlElement(name = "EligibilityRequirementType", required = true)
    protected String eligibilityRequirementType;
    @XmlElement(name = "Quantity")
    protected Integer quantity;
    @XmlElement(name = "CurrencyAmount")
    protected Price currencyAmount;

    /**
     * Gets the value of the eligibilityRequirementType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEligibilityRequirementType() {
        return eligibilityRequirementType;
    }

    /**
     * Sets the value of the eligibilityRequirementType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEligibilityRequirementType(String value) {
        this.eligibilityRequirementType = value;
    }

    /**
     * Gets the value of the quantity property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * Sets the value of the quantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setQuantity(Integer value) {
        this.quantity = value;
    }

    /**
     * Gets the value of the currencyAmount property.
     * 
     * @return
     *     possible object is
     *     {@link Price }
     *     
     */
    public Price getCurrencyAmount() {
        return currencyAmount;
    }

    /**
     * Sets the value of the currencyAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Price }
     *     
     */
    public void setCurrencyAmount(Price value) {
        this.currencyAmount = value;
    }

}
