package com.duboscq.nicolas.mynews.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Nicolas DUBOSCQ on 20/08/2018
 */
public class Keywords {

    @SerializedName("value")
    @Expose
    private String value;

    public String getValue() {
        return value;
    }
}
