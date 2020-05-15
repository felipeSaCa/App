package com.comov.myapplication.datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("date")
    @Expose
    private Date date;
    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("uid")
    @Expose
    private String channelID;

    public Message(String title, Date date, String username, String uidChannel) {
        this.title = title;
        this.date = date;
        this.username = username;
        this.channelID = uidChannel;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getChannelID() {
        return channelID;
    }

    public void setChannelID(String uid) {
        this.channelID = uid;
    }

    public int getNumberListeners(){
        //TODO
        return 1;
    }

}
