package com.comov.myapplication.datamodel;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

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

    @SerializedName("channelID")
    @Expose
    private String channelID;

    public Message(String title, Date date, String username, String uidChannel) {
        this.title = title;
        this.date = date;
        this.username = username;
        this.channelID = uidChannel;
    }

    public Message(String title, String username, String uidChannel) {
        this.title = title;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Message)) return false;
        Message message = (Message) o;
        return Objects.equals(title, message.title) &&
                Objects.equals(date, message.date) &&
                Objects.equals(username, message.username) &&
                Objects.equals(channelID, message.channelID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, date, username, channelID);
    }

    public int getNumberListeners(){
        //TODO
        return 1;
    }

}
