package com.Shirai_Kuroko.DLUTMobile.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ResponseErrorBean {

    @JsonProperty("ret")
    private Integer ret;
    @JsonProperty("errcode")
    private Integer errcode;
    @JsonProperty("errmsg")
    private String errmsg;
    @JsonProperty("data")
    private DataDTO data;

    public Integer getErrcode() {
        return errcode;
    }

    public Integer getRet() {
        return ret;
    }

    public DataDTO getData() {
        return data;
    }

    public String getErrmsg() {
        return errmsg;
    }

    @NoArgsConstructor
    @Data
    public static class DataDTO {
        @JsonProperty("image")
        private String image;

        public String getImage() {
            return image;
        }
    }
}
