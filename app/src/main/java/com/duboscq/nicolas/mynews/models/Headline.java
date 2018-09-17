package com.duboscq.nicolas.mynews.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Nicolas DUBOSCQ on 20/08/2018
 */
public class Headline {

    @SerializedName("main")
    @Expose
    private String main;
    @SerializedName("kicker")
    @Expose
    private String kicker;

    public String getMain() {
        return main;
    }
    public String getKicker() {
        return kicker;
    }
}
