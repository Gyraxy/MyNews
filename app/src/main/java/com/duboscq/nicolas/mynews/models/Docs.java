package com.duboscq.nicolas.mynews.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Nicolas DUBOSCQ on 10/08/2018
 */
public class Docs {

        @SerializedName("web_url")
        @Expose
        private String webUrl;
        @SerializedName("snippet")
        @Expose
        private String snippet;
        @SerializedName("section_name")
        @Expose
        private String section_name;
        @SerializedName("source")
        @Expose
        private String source;
        @SerializedName("pub_date")
        @Expose
        private String pubDate;
        @SerializedName("document_type")
        @Expose
        private String documentType;
        @SerializedName("type_of_material")
        @Expose
        private String typeOfMaterial;
        @SerializedName("_id")
        @Expose
        private String id;
        @SerializedName("word_count")
        @Expose
        private Integer wordCount;
        @SerializedName("score")
        @Expose
        private Double score;
        @SerializedName("headline")
        @Expose
        private Headline headline;
        @SerializedName("keywords")
        @Expose
        private List<Keywords> keywords = null;
        @SerializedName("multimedia")
        @Expose
        private List<Multimedia> multimedia = null;


        public Docs(Headline headline) {
            this.headline=headline;
        }


        public String getWebUrl() {
            return webUrl;
        }

        public void setWebUrl(String webUrl) {
            this.webUrl = webUrl;
        }

        public String getSnippet() {
            return snippet;
        }

        public void setSnippet(String snippet) {
            this.snippet = snippet;
        }

        public String getSection_name() {
            return section_name;
        }

        public void setSection_name(String section_name) {
            this.section_name = section_name;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getPubDate() {
            return pubDate;
        }

        public void setPubDate(String pubDate) {
            this.pubDate = pubDate;
        }

        public String getDocumentType() {
            return documentType;
        }

        public void setDocumentType(String documentType) {
            this.documentType = documentType;
        }

        public String getTypeOfMaterial() {
            return typeOfMaterial;
        }

        public void setTypeOfMaterial(String typeOfMaterial) {
            this.typeOfMaterial = typeOfMaterial;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Integer getWordCount() {
            return wordCount;
        }

        public void setWordCount(Integer wordCount) {
            this.wordCount = wordCount;
        }

        public Double getScore() {
            return score;
        }

        public void setScore(Double score) {
            this.score = score;
        }

        public Headline getHeadline() {
        return headline;
    }

        public List<Keywords> getKeywords() {
            return keywords;
        }

        public void setKeywords(List<Keywords> keywords) {
            this.keywords = keywords;
    }

        public List<Multimedia> getMultimedia() {
            return multimedia;
        }

        public void setMultimedia(List<Multimedia> multimedia) {
            this.multimedia = multimedia;
        }
}
