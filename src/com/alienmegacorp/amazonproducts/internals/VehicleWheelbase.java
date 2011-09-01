
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
 *         &lt;element name="WheelbaseName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="WheelbaseId" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger"/>
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
    "wheelbaseName",
    "wheelbaseId"
})
@XmlRootElement(name = "VehicleWheelbase")
public class VehicleWheelbase {

    @XmlElement(name = "WheelbaseName")
    protected String wheelbaseName;
    @XmlElement(name = "WheelbaseId", required = true)
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger wheelbaseId;

    /**
     * Gets the value of the wheelbaseName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWheelbaseName() {
        return wheelbaseName;
    }

    /**
     * Sets the value of the wheelbaseName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWheelbaseName(String value) {
        this.wheelbaseName = value;
    }

    /**
     * Gets the value of the wheelbaseId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getWheelbaseId() {
        return wheelbaseId;
    }

    /**
     * Sets the value of the wheelbaseId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setWheelbaseId(BigInteger value) {
        this.wheelbaseId = value;
    }

}
