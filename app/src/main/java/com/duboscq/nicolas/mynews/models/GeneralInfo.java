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
    @SerializedName("last_updated")
    @Expose
    private String lastUpdated;
    @SerializedName("results")
    @Expose
    private List<Articles> results = null;

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

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public List<Articles> getResults() {
        return results;
    }

    public List<Articles> setResults(List<Articles> results) {
        this.results = results;
        return results;
    }
}
