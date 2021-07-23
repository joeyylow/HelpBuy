package com.example.helpbuy.ui.settings;

public class FirebaseUser {
    private String Username;
    private String Search;
    private String PhoneNumber;
    private String userID;

    public FirebaseUser(String Username, String Search, String PhoneNumber, String userID) {
        this.Username = Username;
        this.Search = Search;
        this.PhoneNumber = PhoneNumber;
        this.userID = userID;
    }

    public FirebaseUser() {

    }


    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getSearch() {
        return Search;
    }

    public void setSearch(String search) {
        Search = search;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getId() {
        return userID;
    }

    public void setId(String userID) {
        this.userID = userID;
    }

    @Override
    public String toString() {
        return "This user is " +getUsername() ;
    }
}
