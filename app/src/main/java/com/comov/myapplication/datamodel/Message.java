package com.comov.myapplication.datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Message implements Serializable {
    public static final int TEXT_MESSAGE = 0;
    public static final int IMAGE_MESSAGE = 1;
    public static final int LOCATION_MESSAGE = 2;
    public static final int VIDEO_MESSAGE = 3;


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
    @SerializedName("type")
    @Expose
    private int type;


    public Message(String title, Date date, String username, String uidChannel, Integer type) {
        this.title = title;
        this.date = date;
        this.username = username;
        this.channelID = uidChannel;
        this.type = type;
    }

    public Message(String title, String username, String uidChannel, Integer type) {
        this.title = title;
        this.username = username;
        this.channelID = uidChannel;
        this.type = type;
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

    public int getType(){ return type;}

    public void setType(int type) {
        this.type = type;
    }

    public boolean isText(){
        if(type == TEXT_MESSAGE)
            return true;
        return false;
    }

    public boolean isImage(){
        if(type == IMAGE_MESSAGE)
            return true;
        return false;
    }

    public boolean isLocation(){
        if(type == LOCATION_MESSAGE)
            return true;
        return false;
    }

    public boolean isVideo(){
        if(type == VIDEO_MESSAGE)
            return true;
        return false;
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
