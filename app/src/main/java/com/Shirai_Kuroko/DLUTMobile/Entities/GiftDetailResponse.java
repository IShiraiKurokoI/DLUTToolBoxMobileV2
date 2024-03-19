package com.Shirai_Kuroko.DLUTMobile.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Shirai_Kuroko
 * @version 1.0.0
 * @ClassName GiftDetailResponse.java
 * @Description TODO
 * @createTime 2024年03月19日 13:08
 */
@NoArgsConstructor
@Data
public class GiftDetailResponse {
    @JsonProperty("data")
    private DataDTO data;
    @JsonProperty("ret")
    private Integer ret;
    @JsonProperty("errcode")
    private Integer errcode;
    @JsonProperty("errmsg")
    private String errmsg;

    @NoArgsConstructor
    @Data
    public static class DataDTO {
        @JsonProperty("detail")
        private List<DetailDTO> detail;
        @JsonProperty("user_point")
        private UserPointDTO userPoint;

        @NoArgsConstructor
        @Data
        public static class UserPointDTO {
            @JsonProperty("usable_points")
            private String usablePoints;
        }

        @NoArgsConstructor
        @Data
        public static class DetailDTO {
            @JsonProperty("store_count")
            private String storeCount;
            @JsonProperty("remark")
            private String remark;
            @JsonProperty("present_id")
            private String presentId;
            @JsonProperty("name")
            private String name;
            @JsonProperty("image")
            private List<String> image;
            @JsonProperty("need_points")
            private String needPoints;
            @JsonProperty("price")
            private String price;
            @JsonProperty("type")
            private String type;
            @JsonProperty("sale_count")
            private String saleCount;
            @JsonProperty("create_time")
            private String createTime;
            @JsonProperty("product_numbers")
            private String productNumbers;
            @JsonProperty("place")
            private String place;
            @JsonProperty("brand")
            private String brand;
            @JsonProperty("pack")
            private String pack;
            @JsonProperty("status")
            private String status;
        }

        public List<DetailDTO> getDetail() {
            return detail;
        }

        public void setDetail(List<DetailDTO> detail) {
            this.detail = detail;
        }

        public UserPointDTO getUserPoint() {
            return userPoint;
        }

        public void setUserPoint(UserPointDTO userPoint) {
            this.userPoint = userPoint;
        }
    }

    public DataDTO getData() {
        return data;
    }

    public void setData(DataDTO data) {
        this.data = data;
    }

    public Integer getRet() {
        return ret;
    }

    public void setRet(Integer ret) {
        this.ret = ret;
    }

    public Integer getErrcode() {
        return errcode;
    }

    public void setErrcode(Integer errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
}
