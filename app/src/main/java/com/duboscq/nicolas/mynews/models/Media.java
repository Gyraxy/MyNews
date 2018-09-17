package com.duboscq.nicolas.mynews.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Created by Nicolas DUBOSCQ on 10/08/2018
 */
public class Media {

        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("subtype")
        @Expose
        private String subtype;
        @SerializedName("caption")
        @Expose
        private String caption;
        @SerializedName("copyright")
        @Expose
        private String copyright;
        @SerializedName("approved_for_syndication")
        @Expose
        private Integer approvedForSyndication;
        @SerializedName("media-metadata")
        @Expose
        private List<MetaMedia> mediaMetadata = null;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getSubtype() {
            return subtype;
        }

        public void setSubtype(String subtype) {
            this.subtype = subtype;
        }

        public String getCaption() {
            return caption;
        }

        public void setCaption(String caption) {
            this.caption = caption;
        }

        public String getCopyright() {
            return copyright;
        }

        public void setCopyright(String copyright) {
            this.copyright = copyright;
        }

        public Integer getApprovedForSyndication() {
            return approvedForSyndication;
        }

        public void setApprovedForSyndication(Integer approvedForSyndication) {
            this.approvedForSyndication = approvedForSyndication;
        }

        public List<MetaMedia> getMetaMedia() {
            return mediaMetadata;
        }

        public void setMetaMedia(List<MetaMedia> mediaMetadata) {
            this.mediaMetadata = mediaMetadata;
        }
}
