package com.Shirai_Kuroko.DLUTMobile.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class TGTRefreshResult {
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
        @JsonProperty("tgtinfo")
        private List<LoginResponseBean.DataDTO.TgtinfoDTO> tgtinfo;
        @JsonProperty("pword")
        private String pword;
        @JsonProperty("code")
        private Object code;
        @JsonProperty("USER_FIRST_LOGIN")
        private String USER_FIRST_LOGIN;
        @JsonProperty("cas_IS_EXPIRED_PWD")
        private String cas_IS_EXPIRED_PWD;

        public List<LoginResponseBean.DataDTO.TgtinfoDTO> getTgtinfo() {
            return tgtinfo;
        }

        public String getPword() {
            return pword;
        }

        public String getCas_IS_EXPIRED_PWD() {
            return cas_IS_EXPIRED_PWD;
        }

        public String getUSER_FIRST_LOGIN() {
            return USER_FIRST_LOGIN;
        }
    }
}
