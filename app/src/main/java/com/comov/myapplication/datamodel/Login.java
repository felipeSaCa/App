package com.comov.myapplication.datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Login {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("pHash")
    @Expose
    private String pHash;

    public Login(String name, String pHash) {
        this.name = name;
        this.pHash = pHash;
    }

    public String getUsername() { return name;  }

    public void setUsername(String username) {
        this.name = username;
    }

    public String toString() {
        return "Username: " + this.name + " pHash: " + this.pHash;
    }

}
