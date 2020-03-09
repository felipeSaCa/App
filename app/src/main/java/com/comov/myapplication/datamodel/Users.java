package com.comov.myapplication.datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Users implements Serializable
{

    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("pHash")
    @Expose
    private String pHash;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("age")
    @Expose
    private String age;
    private final static long serialVersionUID = 6480304755729039345L;

    public Users(String username, String pHash,String email) {
        this.username = username;
        this.pHash = pHash;
        this.email = email;
    }

    public Users(String username, String pHash,String email, String age) {
        this.username = username;
        this.pHash = pHash;
        this.email = email;
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

}
