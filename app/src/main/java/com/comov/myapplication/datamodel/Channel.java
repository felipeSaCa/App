package com.comov.myapplication.datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.bson.types.ObjectId;
import java.io.Serializable;
import java.util.List;


public class Channel implements Serializable {
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("usuarios")
    @Expose
    private List<String> usuarios;
    @SerializedName("_id")
    @Expose
    private String _id;

    public Channel(String title, List<String> usuarios) {
        this.title = title;
        this.usuarios = usuarios;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<String> usuarios) {
        this.usuarios = usuarios;
    }

    public String get_id() {
        return _id;
    }
}
