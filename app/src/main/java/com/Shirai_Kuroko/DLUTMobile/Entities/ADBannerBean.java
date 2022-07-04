package com.Shirai_Kuroko.DLUTMobile.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ADBannerBean {

    @JsonProperty("PicUrl")
    private String PicUrl;

    @JsonProperty("Url")
    private String Url;

    public String getPicUrl() {
        return PicUrl;
    }

    public String getUrl() {
        return Url;
    }

    public ADBannerBean(String pu,String u)
    {
        PicUrl=pu;
        Url=u;
    }
}