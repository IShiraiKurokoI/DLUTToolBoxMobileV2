package com.Shirai_Kuroko.DLUTMobile.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class NewMsgInfo {

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
        @JsonProperty("msg_info")
        private List<MsgInfoDTO> msgInfo;

        public List<MsgInfoDTO> getMsgInfo() {
            return msgInfo;
        }

        @NoArgsConstructor
        @Data
        public static class MsgInfoDTO {
            @JsonProperty("receipt_opt_json")
            private Object receipt_opt_json;
            @JsonProperty("is_receipt")
            private String is_receipt;
            @JsonProperty("is_authoritative")
            private String is_authoritative;
            @JsonProperty("msg_content")
            private String msg_content;
            @JsonProperty("title")
            private String title;
            @JsonProperty("send_time")
            private String send_time;
            @JsonProperty("create_time")
            private String create_time;
            @JsonProperty("msg_type")
            private String msg_type;
            @JsonProperty("send_uid")
            private String send_uid;
            @JsonProperty("msg_id")
            private String msg_id;
            @JsonProperty("app_id")
            private String app_id;
            @JsonProperty("isCancel")
            private String isCancel;

            public String getMsg_content() {
                return msg_content;
            }

            public Object getReceipt_opt_json() {
                return receipt_opt_json;
            }

            public String getApp_id() {
                return app_id;
            }

            public String getCreate_time() {
                return create_time;
            }

            public String getIs_authoritative() {
                return is_authoritative;
            }

            public String getIs_receipt() {
                return is_receipt;
            }

            public String getIsCancel() {
                return isCancel;
            }

            public String getMsg_id() {
                return msg_id;
            }

            public String getSend_time() {
                return send_time;
            }

            public String getMsg_type() {
                return msg_type;
            }

            public String getSend_uid() {
                return send_uid;
            }

            public String getTitle() {
                return title;
            }
        }
    }
}
