
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
 *         &lt;element name="SpringTypesName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SpringTypesId" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger"/>
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
    "springTypesName",
    "springTypesId"
})
@XmlRootElement(name = "VehicleSpringTypes")
public class VehicleSpringTypes {

    @XmlElement(name = "SpringTypesName")
    protected String springTypesName;
    @XmlElement(name = "SpringTypesId", required = true)
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger springTypesId;

    /**
     * Gets the value of the springTypesName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpringTypesName() {
        return springTypesName;
    }

    /**
     * Sets the value of the springTypesName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpringTypesName(String value) {
        this.springTypesName = value;
    }

    /**
     * Gets the value of the springTypesId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getSpringTypesId() {
        return springTypesId;
    }

    /**
     * Sets the value of the springTypesId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setSpringTypesId(BigInteger value) {
        this.springTypesId = value;
    }

}
