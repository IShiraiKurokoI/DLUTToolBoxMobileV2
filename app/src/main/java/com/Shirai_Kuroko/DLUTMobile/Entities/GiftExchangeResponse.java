package com.Shirai_Kuroko.DLUTMobile.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Shirai_Kuroko
 * @version 1.0.0
 * @ClassName GiftExchangeResponse.java
 * @Description TODO
 * @createTime 2024年03月19日 13:27
 */
@NoArgsConstructor
@Data
public class GiftExchangeResponse {

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
        @JsonProperty("list")
        private ListDTO list;
        @JsonProperty("points_config")
        private PointsConfigDTO pointsConfig;

        @NoArgsConstructor
        @Data
        public static class ListDTO {
            @JsonProperty("need_points")
            private String needPoints;
            @JsonProperty("present_id")
            private String presentId;
            @JsonProperty("id")
            private String id;
            @JsonProperty("points")
            private String points;
            @JsonProperty("type")
            private String type;
            @JsonProperty("is_exchange")
            private String isExchange;
            @JsonProperty("reason")
            private String reason;
            @JsonProperty("create_time")
            private String createTime;
            @JsonProperty("name")
            private String name;
            @JsonProperty("exchange_time")
            private String exchangeTime;
            @JsonProperty("image")
            private List<String> image;
            @JsonProperty("status")
            private String status;
            @JsonProperty("end_time")
            private String endTime;
        }

        @NoArgsConstructor
        @Data
        public static class PointsConfigDTO {
            @JsonProperty("point_limit")
            private PointLimitDTO pointLimit;
            @JsonProperty("points_explain_set")
            private String pointsExplainSet;
            @JsonProperty("get_explain_set")
            private String getExplainSet;
            @JsonProperty("get_gift_datetime")
            private GetGiftDatetimeDTO getGiftDatetime;

            @NoArgsConstructor
            @Data
            public static class PointLimitDTO {
                @JsonProperty("get_limit")
                private String getLimit;
                @JsonProperty("consumption_limit")
                private String consumptionLimit;
            }

            @NoArgsConstructor
            @Data
            public static class GetGiftDatetimeDTO {
                @JsonProperty("begin_time")
                private String beginTime;
                @JsonProperty("end_time")
                private String endTime;
            }

            public PointLimitDTO getPointLimit() {
                return pointLimit;
            }

            public String getPointsExplainSet() {
                return pointsExplainSet;
            }

            public String getGetExplainSet() {
                return getExplainSet;
            }

            public GetGiftDatetimeDTO getGetGiftDatetime() {
                return getGiftDatetime;
            }
        }

        public ListDTO getList() {
            return list;
        }

        public PointsConfigDTO getPointsConfig() {
            return pointsConfig;
        }
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

    public String getErrmsg() {
        return errmsg;
    }
}
