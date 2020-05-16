package com.comov.myapplication.datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Contact implements Serializable {
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("contact")
    @Expose
    private String contact;

    public Contact(String username, String contact) {
        this.username = username;
        this.contact = contact;
    }
}
