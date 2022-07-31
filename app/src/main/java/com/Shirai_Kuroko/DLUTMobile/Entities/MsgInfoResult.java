package com.Shirai_Kuroko.DLUTMobile.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class MsgInfoResult {

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
        private List<MsgInfoDTO> msg_info;

        public List<MsgInfoDTO> getMsg_info() {
            return msg_info;
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
            @JsonProperty("app_info")
            private AppInfoDTO app_info;

            public String getApp_id() {
                return app_id;
            }

            public String getMsg_content() {
                return msg_content;
            }

            @NoArgsConstructor
            @Data
            public static class AppInfoDTO {
                @JsonProperty("app_name")
                private String appName;
                @JsonProperty("app_id")
                private String appId;
                @JsonProperty("url")
                private String url;
                @JsonProperty("type")
                private String type;
                @JsonProperty("platform")
                private String platform;
                @JsonProperty("describe")
                private String describe;
                @JsonProperty("popularity")
                private String popularity;
                @JsonProperty("recommend")
                private String recommend;
                @JsonProperty("iswhistle")
                private String iswhistle;
                @JsonProperty("is_school_official")
                private String isSchoolOfficial;
                @JsonProperty("recommend_icon")
                private String recommendIcon;
                @JsonProperty("screenshot")
                private List<String> screenshot;
                @JsonProperty("icon")
                private String icon;
                @JsonProperty("onsale")
                private String onsale;
                @JsonProperty("custom_info")
                private CustomInfoDTO customInfo;
                @JsonProperty("open_with")
                private String openWith;
                @JsonProperty("menu")
                private MenuDTO menu;
                @JsonProperty("modify_time")
                private String modifyTime;

                @NoArgsConstructor
                @Data
                public static class CustomInfoDTO {
                }

                @NoArgsConstructor
                @Data
                public static class MenuDTO {
                }
            }
        }
    }
}
