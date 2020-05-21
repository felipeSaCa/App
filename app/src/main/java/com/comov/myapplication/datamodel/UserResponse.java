package com.comov.myapplication.datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class UserResponse implements Serializable {
    @SerializedName("userObj")
    @Expose
    private List<Users> users;

    public List<Users> getUsers() {
        return users;
    }

    public void setUserss(List<Users> users) {
        this.users = users;
    }

}
