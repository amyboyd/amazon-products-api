
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
 *         &lt;element name="BedName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BedId" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger"/>
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
    "bedName",
    "bedId"
})
@XmlRootElement(name = "VehicleBed")
public class VehicleBed {

    @XmlElement(name = "BedName")
    protected String bedName;
    @XmlElement(name = "BedId", required = true)
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger bedId;

    /**
     * Gets the value of the bedName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBedName() {
        return bedName;
    }

    /**
     * Sets the value of the bedName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBedName(String value) {
        this.bedName = value;
    }

    /**
     * Gets the value of the bedId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getBedId() {
        return bedId;
    }

    /**
     * Sets the value of the bedId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setBedId(BigInteger value) {
        this.bedId = value;
    }

}
