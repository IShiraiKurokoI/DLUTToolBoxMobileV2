package com.Shirai_Kuroko.DLUTMobile.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class PresentListResult {

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
        @JsonProperty("total")
        private String total;
        @JsonProperty("list")
        private List<ListDTO> list;
        @JsonProperty("present_type")
        private List<PresentTypeDTO> present_type;
        @JsonProperty("points_range")
        private List<PointsRangeDTO> points_range;

        public List<ListDTO> getList() {
            return list;
        }

        @NoArgsConstructor
        @Data
        public static class ListDTO {
            @JsonProperty("status")
            private String status;
            @JsonProperty("remark")
            private String remark;
            @JsonProperty("present_id")
            private String present_id;
            @JsonProperty("store_count")
            private String store_count;
            @JsonProperty("name")
            private String name;
            @JsonProperty("image")
            private List<String> image;
            @JsonProperty("need_points")
            private String need_points;
            @JsonProperty("price")
            private String price;
            @JsonProperty("type")
            private String type;
            @JsonProperty("sale_count")
            private String sale_count;
            @JsonProperty("create_time")
            private String create_time;
            @JsonProperty("product_numbers")
            private String product_numbers;
            @JsonProperty("place")
            private String place;
            @JsonProperty("brand")
            private String brand;
            @JsonProperty("pack")
            private String pack;

            public String getName() {
                return name;
            }

            //获取简介
            public String getRemark() {
                return remark;
            }

            //获取库存
            public String getStoreCount() {
                return store_count;
            }

            //获取头图
            public List<String> getImage() {
                return image;
            }

            //获取原价
            public String getPrice() {
                return price;
            }

            //获取所需点数
            public String getNeedPoints() {
                return need_points;
            }

            //获取ID
            public String getPresentId() {
                return present_id;
            }

            //获取已卖出件数
            public String getSaleCount() {
                return sale_count;
            }
        }

        @NoArgsConstructor
        @Data
        public static class PresentTypeDTO {
            @JsonProperty("config_name")
            private String config_name;
            @JsonProperty("config_value")
            private String config_value;
        }

        @NoArgsConstructor
        @Data
        public static class PointsRangeDTO {
            @JsonProperty("config_name")
            private String config_name;
            @JsonProperty("config_value")
            private String config_value;
        }
    }
}
