
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
 *         &lt;element name="Year" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger"/>
 *         &lt;element name="IsValid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element ref="{http://webservices.amazon.com/AWSECommerceService/2010-04-01}VehicleMakes" minOccurs="0"/>
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
    "year",
    "isValid",
    "vehicleMakes"
})
@XmlRootElement(name = "VehicleYear")
public class VehicleYear {

    @XmlElement(name = "Year", required = true)
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger year;
    @XmlElement(name = "IsValid")
    protected String isValid;
    @XmlElement(name = "VehicleMakes")
    protected VehicleMakes vehicleMakes;

    /**
     * Gets the value of the year property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getYear() {
        return year;
    }

    /**
     * Sets the value of the year property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setYear(BigInteger value) {
        this.year = value;
    }

    /**
     * Gets the value of the isValid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsValid() {
        return isValid;
    }

    /**
     * Sets the value of the isValid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsValid(String value) {
        this.isValid = value;
    }

    /**
     * Gets the value of the vehicleMakes property.
     * 
     * @return
     *     possible object is
     *     {@link VehicleMakes }
     *     
     */
    public VehicleMakes getVehicleMakes() {
        return vehicleMakes;
    }

    /**
     * Sets the value of the vehicleMakes property.
     * 
     * @param value
     *     allowed object is
     *     {@link VehicleMakes }
     *     
     */
    public void setVehicleMakes(VehicleMakes value) {
        this.vehicleMakes = value;
    }

}
