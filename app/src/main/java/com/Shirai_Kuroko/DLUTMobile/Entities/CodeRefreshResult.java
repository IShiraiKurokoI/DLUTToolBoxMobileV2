package com.Shirai_Kuroko.DLUTMobile.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CodeRefreshResult {

    @JsonProperty("data")
    private DataDTO data;
    @JsonProperty("ret")
    private Integer ret;
    @JsonProperty("errcode")
    private Integer errcode;
    @JsonProperty("errmsg")
    private String errmsg;

    public String getErrmsg() {
        return errmsg;
    }

    public DataDTO getData() {
        return data;
    }

    public Integer getRet() {
        return ret;
    }

    public Integer getErrcode() {
        return errcode;
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
