package com.example.helpbuy.ui.listoffer;

import com.google.firebase.firestore.PropertyName;

public class Offers {
    private String Location;

    @PropertyName("Date of Purchase")
    private String dateOfPurchase;

    @PropertyName("Min. Fees Request")
    private String minFeesRequest;

    private String Remarks;
    private String Duration;

    private String UID;
    private String aUID;

    private String DOCID;


    public Offers(String location, String dateOfPurchase, String minFeesRequest, String remarks,
                  String duration, String uid, String aUID, String docID) {
        this.Location = location;
        this.dateOfPurchase = dateOfPurchase;
        this.minFeesRequest = minFeesRequest;
        this.Remarks = remarks;
        this.Duration = duration;
        this.UID = uid;
        this.aUID = aUID;
        this.DOCID = docID;
    }
    public Offers() {}


    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    @PropertyName("Date of Purchase")
    public String getDateOfPurchase() {
        return dateOfPurchase;
    }

    @PropertyName("Date of Purchase")
    public void setDateOfPurchase(String dateOfPurchase) {
        this.dateOfPurchase = dateOfPurchase;
    }

    @PropertyName("Min. Fees Request")
    public String getMinFeesRequest() {
        return minFeesRequest;
    }

    @PropertyName("Min. Fees Request")
    public void setMinFeesRequest(String minFeesRequest) {
        this.minFeesRequest = minFeesRequest;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
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


    public void setdocID(String docID) { this.DOCID = docID; }
    public String getdocID() {return DOCID; }
}
