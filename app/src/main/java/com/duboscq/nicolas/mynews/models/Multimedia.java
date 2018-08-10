package com.duboscq.nicolas.mynews.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Nicolas DUBOSCQ on 08/08/2018
 */
public class Multimedia {

    @SerializedName("url")
    @Expose
    private String url;

    public String getUrl() {
        return url;
    }

}
