package com.Shirai_Kuroko.DLUTMobile.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class ApplicationConfig {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("category")
    private String category;
    @JsonProperty("app_name")
    private String app_name;
    @JsonProperty("icon")
    private String icon;
    @JsonProperty("describe")
    private String describe;
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
}
