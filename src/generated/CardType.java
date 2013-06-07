//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.06.06 at 03:58:38 PM CEST 
//


package generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for cardType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="cardType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="openings">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="top" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *                   &lt;element name="bottom" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *                   &lt;element name="left" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *                   &lt;element name="right" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="pin">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="playerID" type="{http://www.w3.org/2001/XMLSchema}int" maxOccurs="4" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="treasure" type="{}treasureType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "cardType", propOrder = {
    "openings",
    "pin",
    "treasure"
})
public class CardType {

    @XmlElement(required = true)
    protected CardType.Openings openings;
    @XmlElement(required = true)
    protected CardType.Pin pin;
    @XmlElement(required = true)
    protected TreasureType treasure;

    /**
     * Gets the value of the openings property.
     * 
     * @return
     *     possible object is
     *     {@link CardType.Openings }
     *     
     */
    public CardType.Openings getOpenings() {
        return openings;
    }

    /**
     * Sets the value of the openings property.
     * 
     * @param value
     *     allowed object is
     *     {@link CardType.Openings }
     *     
     */
    public void setOpenings(CardType.Openings value) {
        this.openings = value;
    }

    /**
     * Gets the value of the pin property.
     * 
     * @return
     *     possible object is
     *     {@link CardType.Pin }
     *     
     */
    public CardType.Pin getPin() {
        return pin;
    }

    /**
     * Sets the value of the pin property.
     * 
     * @param value
     *     allowed object is
     *     {@link CardType.Pin }
     *     
     */
    public void setPin(CardType.Pin value) {
        this.pin = value;
    }

    /**
     * Gets the value of the treasure property.
     * 
     * @return
     *     possible object is
     *     {@link TreasureType }
     *     
     */
    public TreasureType getTreasure() {
        return treasure;
    }

    /**
     * Sets the value of the treasure property.
     * 
     * @param value
     *     allowed object is
     *     {@link TreasureType }
     *     
     */
    public void setTreasure(TreasureType value) {
        this.treasure = value;
    }


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
     *         &lt;element name="top" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
     *         &lt;element name="bottom" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
     *         &lt;element name="left" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
     *         &lt;element name="right" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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
        "top",
        "bottom",
        "left",
        "right"
    })
    public static class Openings {

        protected boolean top;
        protected boolean bottom;
        protected boolean left;
        protected boolean right;

        /**
         * Gets the value of the top property.
         * 
         */
        public boolean isTop() {
            return top;
        }

        /**
         * Sets the value of the top property.
         * 
         */
        public void setTop(boolean value) {
            this.top = value;
        }

        /**
         * Gets the value of the bottom property.
         * 
         */
        public boolean isBottom() {
            return bottom;
        }

        /**
         * Sets the value of the bottom property.
         * 
         */
        public void setBottom(boolean value) {
            this.bottom = value;
        }

        /**
         * Gets the value of the left property.
         * 
         */
        public boolean isLeft() {
            return left;
        }

        /**
         * Sets the value of the left property.
         * 
         */
        public void setLeft(boolean value) {
            this.left = value;
        }

        /**
         * Gets the value of the right property.
         * 
         */
        public boolean isRight() {
            return right;
        }

        /**
         * Sets the value of the right property.
         * 
         */
        public void setRight(boolean value) {
            this.right = value;
        }

    }


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
     *         &lt;element name="playerID" type="{http://www.w3.org/2001/XMLSchema}int" maxOccurs="4" minOccurs="0"/>
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
        "playerID"
    })
    public static class Pin {

        @XmlElement(type = Integer.class)
        protected List<Integer> playerID;

        /**
         * Gets the value of the playerID property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the playerID property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getPlayerID().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Integer }
         * 
         * 
         */
        public List<Integer> getPlayerID() {
            if (playerID == null) {
                playerID = new ArrayList<Integer>();
            }
            return this.playerID;
        }

    }

}
