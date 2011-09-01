
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
 *         &lt;element name="DriveTypeName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DriveTypeId" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger"/>
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
    "driveTypeName",
    "driveTypeId"
})
@XmlRootElement(name = "VehicleDriveType")
public class VehicleDriveType {

    @XmlElement(name = "DriveTypeName")
    protected String driveTypeName;
    @XmlElement(name = "DriveTypeId", required = true)
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger driveTypeId;

    /**
     * Gets the value of the driveTypeName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDriveTypeName() {
        return driveTypeName;
    }

    /**
     * Sets the value of the driveTypeName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDriveTypeName(String value) {
        this.driveTypeName = value;
    }

    /**
     * Gets the value of the driveTypeId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getDriveTypeId() {
        return driveTypeId;
    }

    /**
     * Sets the value of the driveTypeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setDriveTypeId(BigInteger value) {
        this.driveTypeId = value;
    }

}
