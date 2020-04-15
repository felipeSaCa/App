package com.comov.myapplication.datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Users implements Serializable
{

    @SerializedName("name")
    @Expose
    private String name;
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

    public Users(String name, String pHash, String email) {
        this.name = name;
        this.pHash = pHash;
        this.email = email;
    }

    public Users(String name, String pHash, String email, String age) {
        this.name = name;
        this.pHash = pHash;
        this.email = email;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
