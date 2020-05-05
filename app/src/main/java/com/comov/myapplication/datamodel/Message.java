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
    @SerializedName("body")
    @Expose
    private String body;

    @SerializedName("uid")
    @Expose
    private String uid;

    public Message(String title, Date date, String body, String uid) {
        this.title = title;
        this.date = date;
        this.body = body;
        this.uid = uid;
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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getNumberListeners(){
        //TODO
        return 1;
    }

}
