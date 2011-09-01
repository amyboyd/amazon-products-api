
package com.alienmegacorp.amazonproducts.internals;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PromotionEligibilityRequirements complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PromotionEligibilityRequirements">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="EligibilityRequirement" type="{http://webservices.amazon.com/AWSECommerceService/2010-04-01}PromotionEligibilityRequirement" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PromotionEligibilityRequirements", propOrder = {
    "eligibilityRequirement"
})
public class PromotionEligibilityRequirements {

    @XmlElement(name = "EligibilityRequirement")
    protected List<PromotionEligibilityRequirement> eligibilityRequirement;

    /**
     * Gets the value of the eligibilityRequirement property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the eligibilityRequirement property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEligibilityRequirement().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PromotionEligibilityRequirement }
     * 
     * 
     */
    public List<PromotionEligibilityRequirement> getEligibilityRequirement() {
        if (eligibilityRequirement == null) {
            eligibilityRequirement = new ArrayList<PromotionEligibilityRequirement>();
        }
        return this.eligibilityRequirement;
    }

}
