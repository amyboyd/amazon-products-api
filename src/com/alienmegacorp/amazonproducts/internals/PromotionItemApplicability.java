
package com.alienmegacorp.amazonproducts.internals;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PromotionItemApplicability complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PromotionItemApplicability">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ASIN" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="IsInBenefitSet" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="IsInEligibilityRequirementSet" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PromotionItemApplicability", propOrder = {
    "asin",
    "isInBenefitSet",
    "isInEligibilityRequirementSet"
})
public class PromotionItemApplicability {

    @XmlElement(name = "ASIN", required = true)
    protected String asin;
    @XmlElement(name = "IsInBenefitSet")
    protected boolean isInBenefitSet;
    @XmlElement(name = "IsInEligibilityRequirementSet")
    protected boolean isInEligibilityRequirementSet;

    /**
     * Gets the value of the asin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getASIN() {
        return asin;
    }

    /**
     * Sets the value of the asin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setASIN(String value) {
        this.asin = value;
    }

    /**
     * Gets the value of the isInBenefitSet property.
     * 
     */
    public boolean isIsInBenefitSet() {
        return isInBenefitSet;
    }

    /**
     * Sets the value of the isInBenefitSet property.
     * 
     */
    public void setIsInBenefitSet(boolean value) {
        this.isInBenefitSet = value;
    }

    /**
     * Gets the value of the isInEligibilityRequirementSet property.
     * 
     */
    public boolean isIsInEligibilityRequirementSet() {
        return isInEligibilityRequirementSet;
    }

    /**
     * Sets the value of the isInEligibilityRequirementSet property.
     * 
     */
    public void setIsInEligibilityRequirementSet(boolean value) {
        this.isInEligibilityRequirementSet = value;
    }

}
