package com.duboscq.nicolas.mynews.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Nicolas DUBOSCQ on 08/08/2018
 */
public class Articles {

    @SerializedName("section")
    @Expose
    private String section;
    @SerializedName("subsection")
    @Expose
    private String subsection;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("byline")
    @Expose
    private String byline;
    @SerializedName("published_date")
    @Expose
    private String publishedDate;
    @SerializedName("multimedia")
    @Expose
    private List<Multimedia> multimedia = null;
    @SerializedName("media")
    @Expose
    private List<Media> media = null;


    public String getSection() {
        return section;
    }

    public String getSubsection() {
        return subsection;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getByline() {
        return byline;
    }

    public void setByline(String byline) {
        this.byline = byline;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public List<Multimedia> getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(List<Multimedia> multimedia) {
        this.multimedia = multimedia;
    }

    public List<Media> getMedia() {
        return media;
    }

    public void setMedia(List<Media> media) {
        this.media = media;
    }

}
