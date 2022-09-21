package com.Shirai_Kuroko.DLUTMobile.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ApplicationConfig {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("app_id")
    private String app_id;
    @JsonProperty("category")
    private String category;
    @JsonProperty("app_name")
    private String app_name;
    @JsonProperty("icon")
    private String icon;
    @JsonProperty("describe")
    private String describe;
    @JsonProperty("type")
    private String type;
    @JsonProperty("popularity")
    private String popularity;
    @JsonProperty("url")
    private String url;
    @JsonProperty("issubscription")
    private Integer issubscription;

    public Integer getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public String getAppName() {
        return app_name;
    }

    public String getDescribe() {
        return describe;
    }

    public Integer getIssubscription() {
        return issubscription;
    }

    public String getIcon() {
        return icon;
    }

    public String getPopularity() {
        return popularity;
    }

    public String getUrl() {
        return url;
    }

    public void setIssubscription(Integer issubscription) {
        this.issubscription = issubscription;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }
}
