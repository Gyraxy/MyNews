package com.duboscq.nicolas.mynews.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Nicolas DUBOSCQ on 08/08/2018
 */
public class GeneralInfo {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("copyright")
    @Expose
    private String copyright;
    @SerializedName("results")
    @Expose
    private List<Articles> results = null;
    @SerializedName("docs")
    @Expose
    private List<Docs> docs = null;


    public GeneralInfo(String status,List<Articles> results) {
        this.status=status;
        this.results = results;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public List<Articles> getResults() {
        return results;
    }

    public List<Articles> setResults(List<Articles> results) {
        this.results = results;
        return results;
    }
    public List<Docs> getDocs() {
        return docs;
    }

    public List<Docs> setDocs(List<Docs> docs) {
        this.docs = docs;
        return docs;
    }
}
