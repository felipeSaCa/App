package com.comov.myapplication.datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class MessageResponse implements Serializable {
    @SerializedName("MessagesObj")
    @Expose
    private List<Message> messages;

    public List<Message> getMessages(){
        return messages;
    }
}
