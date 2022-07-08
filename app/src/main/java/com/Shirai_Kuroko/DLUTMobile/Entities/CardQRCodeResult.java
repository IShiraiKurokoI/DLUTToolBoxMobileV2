package com.Shirai_Kuroko.DLUTMobile.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CardQRCodeResult {

    @JsonProperty("data")
    private DataDTO data;
    @JsonProperty("ret")
    private Integer ret;
    @JsonProperty("errcode")
    private Integer errcode;
    @JsonProperty("errmsg")
    private String errmsg;

    public DataDTO getData() {
        return data;
    }

    @NoArgsConstructor
    @Data
    public static class DataDTO {
        @JsonProperty("eStr")
        private String eStr;

        public String getUrl() {
            return url;
        }

        @JsonProperty("url")
        private String url;

        public String geteStr() {
            return eStr;
        }
    }
}
