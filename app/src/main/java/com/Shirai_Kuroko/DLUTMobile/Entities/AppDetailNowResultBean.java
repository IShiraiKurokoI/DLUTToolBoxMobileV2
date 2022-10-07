package com.Shirai_Kuroko.DLUTMobile.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Shirai_Kuroko
 * @version 1.0.0
 * @ClassName AppDetailNowResultBean.java
 * @Description TODO
 * @createTime 2022年10月06日 17:42
 */
@NoArgsConstructor
@Data
public class AppDetailNowResultBean {

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

    public String getErrmsg() {
        return errmsg;
    }

    @NoArgsConstructor
    @Data
    public static class DataDTO {
        @JsonProperty("app_name")
        private String app_name;
        @JsonProperty("isNewApp")
        private Integer isNewApp;
        @JsonProperty("category")
        private String category;
        @JsonProperty("app_id")
        private String app_id;
        @JsonProperty("url")
        private String url;
        @JsonProperty("type")
        private String type;
        @JsonProperty("status")
        private String status;
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
        private String is_school_official;
        @JsonProperty("iscollection")
        private Integer iscollection;
        @JsonProperty("isCelRe")
        private Integer isCelRe;
        @JsonProperty("issubscription")
        private Integer issubscription;
        @JsonProperty("recommend_icon")
        private String recommend_icon;
        @JsonProperty("screenshot")
        private List<String> screenshot;
        @JsonProperty("icon")
        private String icon;
        @JsonProperty("onsale")
        private OnsaleDTO onsale;
        @JsonProperty("custom_info")
        private CustomInfoDTO custom_info;
        @JsonProperty("open_with")
        private String open_with;
        @JsonProperty("menu")
        private Object menu;
        @JsonProperty("modify_time")
        private String modify_time;
        @JsonProperty("is_sso")
        private Integer is_sso;
        @JsonProperty("android_pakage_name")
        private String android_pakage_name;
        @JsonProperty("android_pakage_url")
        private String android_pakage_url;
        @JsonProperty("ios_pakage_name")
        private String ios_pakage_name;
        @JsonProperty("ios_pakage_url")
        private String ios_pakage_url;
        @JsonProperty("program_id")
        private String program_id;
        @JsonProperty("program_url")
        private String program_url;
        @JsonProperty("program_type")
        private String program_type;
        @JsonProperty("developer")
        private DeveloperDTO developer;
        @JsonProperty("score")
        private ScoreDTO score;

        public ScoreDTO getScore() {
            return score;
        }

        public String getPopularity() {
            return popularity;
        }

        @NoArgsConstructor
        @Data
        public static class OnsaleDTO {
        }

        @NoArgsConstructor
        @Data
        public static class CustomInfoDTO {
        }

        @NoArgsConstructor
        @Data
        public static class DeveloperDTO {
            @JsonProperty("jid")
            private Integer jid;
            @JsonProperty("name")
            private String name;
            @JsonProperty("organization")
            private String organization;
        }

        @NoArgsConstructor
        @Data
        public static class ScoreDTO {
            @JsonProperty("total")
            private Integer total;
            @JsonProperty("times")
            private Integer times;
            @JsonProperty("average")
            private Double average;

            public Double getAverage() {
                return average;
            }

            public Integer getTimes() {
                return times;
            }

            public Integer getTotal() {
                return total;
            }
        }
    }
}
