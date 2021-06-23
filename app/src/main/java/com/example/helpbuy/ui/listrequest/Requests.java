package com.example.helpbuy.ui.listrequest;

import com.google.firebase.firestore.PropertyName;

public class Requests {
    private String Item;
    private String Location;

    @PropertyName("Delivery Date")
    private String deliveryDate;
    @PropertyName("Delivery Time")
    private String deliveryTime;

    @PropertyName("Delivery Fees")
    private String deliveryFees;

    private String Quantity;
    private String Remarks;

    @PropertyName("Estimated Price")
    private String estimatedPrice;
    private String UID;
    private String aUID;

    public Requests(String item, String location, String deliveryDate, String deliveryTime, String deliveryFees, String quantity, String remarks, String estimatedPrice, String UID, String aUID){
        this.Item = item;
        this.Location = location;
        this.deliveryDate = deliveryDate;
        this.deliveryTime = deliveryTime;
        this.deliveryFees = deliveryFees;
        this.Quantity = quantity;
        this.Remarks = remarks;
        this.estimatedPrice = estimatedPrice;
        this.UID = UID;
        this.aUID = aUID;
    }

    public Requests() {}


    public String getItem() {
        return Item;
    }

    public void setItem(String item) {
        Item = item;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    @PropertyName("Delivery Date")
    public String getDeliveryDate() {
        return deliveryDate;
    }

    @PropertyName("Delivery Date")
    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    @PropertyName("Delivery Time")
    public String getDeliveryTime() {
        return deliveryTime;
    }

    @PropertyName("Delivery Time")
    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    @PropertyName("Delivery Fees")
    public String getDeliveryFees() {
        return deliveryFees;
    }

    @PropertyName("Delivery Fees")
    public void setDeliveryFees(String deliveryFees) {
        this.deliveryFees = deliveryFees;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }

    @PropertyName("Estimated Price")
    public String getEstimatedPrice() {
        return estimatedPrice;
    }

    @PropertyName("Estimated Price")
    public void setEstimatedPrice(String estimatedPrice) {
        this.estimatedPrice = estimatedPrice;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getaUID() {
        return aUID;
    }

    public void setaUID(String aUID) {
        this.aUID = aUID;
    }
}
