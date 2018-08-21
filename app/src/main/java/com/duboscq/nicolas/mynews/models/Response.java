package com.duboscq.nicolas.mynews.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Nicolas DUBOSCQ on 19/08/2018
 */
public class Response {

    @SerializedName("docs")
    @Expose
    private List<Docs> docs = null;

    public List<Docs> getDocs() {
        return docs;
    }

    public void setDocs(List<Docs> docs) {
        this.docs = docs;
    }
}