package com.Shirai_Kuroko.DLUTMobile.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class HeadUploadResult {

    @JsonProperty("error")
    private Integer error;
    @JsonProperty("uri")
    private String uri;
    @JsonProperty("url")
    private String url;
    @JsonProperty("url")
    private String uRL;
    @JsonProperty("file_uri")
    private String fileUri;
    @JsonProperty("file_url")
    private String fileUrl;

    public String getUri() {
        return uri;
    }
}
