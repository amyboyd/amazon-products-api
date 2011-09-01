
package com.alienmegacorp.amazonproducts.internals;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
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
 *         &lt;element name="BrakesName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BrakesId" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger"/>
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
    "brakesName",
    "brakesId"
})
@XmlRootElement(name = "VehicleBrakes")
public class VehicleBrakes {

    @XmlElement(name = "BrakesName")
    protected String brakesName;
    @XmlElement(name = "BrakesId", required = true)
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger brakesId;

    /**
     * Gets the value of the brakesName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBrakesName() {
        return brakesName;
    }

    /**
     * Sets the value of the brakesName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBrakesName(String value) {
        this.brakesName = value;
    }

    /**
     * Gets the value of the brakesId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getBrakesId() {
        return brakesId;
    }

    /**
     * Sets the value of the brakesId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setBrakesId(BigInteger value) {
        this.brakesId = value;
    }

}
