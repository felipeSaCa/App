package com.comov.myapplication.datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Login {
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("pHash")
    @Expose
    private String pHash;

    public Login(String username, String pHash) {
        this.username = username;
        this.pHash = pHash;
    }

    public String getUsername() { return username;  }

    public void setUsername(String username) {
        this.username = username;
    }

}
