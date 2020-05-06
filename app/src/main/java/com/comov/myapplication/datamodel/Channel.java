package com.comov.myapplication.datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Channel implements Serializable {
    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("usuarios")
    @Expose
    private String usuarios;

    @SerializedName("uid")
    @Expose
    private String uid;

    public Channel(String title, String usuarios, String uid) {
        this.title = title;
        this.usuarios = usuarios;
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(String usuarios) {
        this.usuarios = usuarios;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
