package com.Shirai_Kuroko.DLUTMobile.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class MsgResult {

    @JsonProperty("data")
    private DataDTO data;
    @JsonProperty("ret")
    private Integer ret;
    @JsonProperty("errcode")
    private Integer errcode;
    @JsonProperty("errmsg")
    private String errmsg;

    public Integer getRet() {
        return ret;
    }

    public DataDTO getData() {
        return data;
    }

    @NoArgsConstructor
    @Data
    public static class DataDTO {
        @JsonProperty("msg_id")
        private List<String> msg_id;
        @JsonProperty("conver_msg_id")
        private List<String> conver_msg_id;
        @JsonProperty("app_msg_id")
        private List<String> app_msg_id;
        @JsonProperty("id_photo")
        private String id_photo;

        public List<String> getAppMsgId() {
            return app_msg_id;
        }

        public List<String> getConverMsgId() {
            return conver_msg_id;
        }

        public List<String> getMsgId() {
            return msg_id;
        }


    }
}
