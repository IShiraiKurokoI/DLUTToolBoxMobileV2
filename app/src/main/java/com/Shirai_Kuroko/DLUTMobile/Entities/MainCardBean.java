package com.Shirai_Kuroko.DLUTMobile.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Shirai_Kuroko
 * @version 1.0.0
 * @ClassName MainCardBean.java
 * @Description TODO
 * @createTime 2022年09月05日 09:58
 */
@NoArgsConstructor
@Data
public class MainCardBean {

    @JsonProperty("total")
    private String total;
    @JsonProperty("list_data")
    private List<MainCardDataBean> list_data;

    public List<MainCardDataBean> getList_data() {
        return list_data;
    }

    @NoArgsConstructor
    @Data
    public static class MainCardDataBean {
        @JsonProperty("app_name")
        private String app_name;
        @JsonProperty("app_id")
        private String app_id;
        @JsonProperty("sort")
        private String sort;
        @JsonProperty("add_time")
        private String add_time;
        @JsonProperty("status")
        private String status;
        @JsonProperty("icon")
        private String icon;
        @JsonProperty("card_url")
        private String card_url;
        @JsonProperty("card_img_url")
        private CardImgUrlDTO card_img_url;
        @JsonProperty("popularity")
        private String popularity;
        @JsonProperty("describe")
        private String describe;
        @JsonProperty("type")
        private String type;
        @JsonProperty("url")
        private String url;
        @JsonProperty("open_with")
        private String openWith;
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

        public String getApp_name() {
            return app_name;
        }

        public void setApp_name(String app_name) {
            this.app_name = app_name;
        }

        public String getApp_id() {
            return app_id;
        }

        public void setApp_id(String app_id) {
            this.app_id = app_id;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getCard_url() {
            return card_url;
        }

        public void setCard_url(String card_url) {
            this.card_url = card_url;
        }

        public CardImgUrlDTO getCard_img_url() {
            return card_img_url;
        }

        public void setCard_img_url(CardImgUrlDTO card_img_url) {
            this.card_img_url = card_img_url;
        }

        public String getPopularity() {
            return popularity;
        }

        public void setPopularity(String popularity) {
            this.popularity = popularity;
        }

        public String getDescribe() {
            return describe;
        }

        public void setDescribe(String describe) {
            this.describe = describe;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getOpenWith() {
            return openWith;
        }

        public void setOpenWith(String openWith) {
            this.openWith = openWith;
        }

        public String getAndroid_pakage_name() {
            return android_pakage_name;
        }

        public void setAndroid_pakage_name(String android_pakage_name) {
            this.android_pakage_name = android_pakage_name;
        }

        public String getAndroid_pakage_url() {
            return android_pakage_url;
        }

        public void setAndroid_pakage_url(String android_pakage_url) {
            this.android_pakage_url = android_pakage_url;
        }

        public String getIos_pakage_name() {
            return ios_pakage_name;
        }

        public void setIos_pakage_name(String ios_pakage_name) {
            this.ios_pakage_name = ios_pakage_name;
        }

        public String getIos_pakage_url() {
            return ios_pakage_url;
        }

        public void setIos_pakage_url(String ios_pakage_url) {
            this.ios_pakage_url = ios_pakage_url;
        }

        public String getProgram_id() {
            return program_id;
        }

        public void setProgram_id(String program_id) {
            this.program_id = program_id;
        }

        public String getProgram_url() {
            return program_url;
        }

        public void setProgram_url(String program_url) {
            this.program_url = program_url;
        }

        public String getProgram_type() {
            return program_type;
        }

        public void setProgram_type(String program_type) {
            this.program_type = program_type;
        }

        @NoArgsConstructor
        @Data
        public static class CardImgUrlDTO {
        }
    }
}
