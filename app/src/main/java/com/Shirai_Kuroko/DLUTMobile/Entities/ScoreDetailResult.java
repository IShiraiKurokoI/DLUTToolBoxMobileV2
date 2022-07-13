package com.Shirai_Kuroko.DLUTMobile.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ScoreDetailResult {

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
        @JsonProperty("points_config")
        private PointsConfigDTO pointsConfig;

        public List<ListDTO> getList() {
            return list;
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
                private Integer beginTime;
                @JsonProperty("end_time")
                private Integer endTime;
            }
        }

        @NoArgsConstructor
        @Data
        public static class ListDTO {
            @JsonProperty("points")
            private String points;
            @JsonProperty("time")
            private String time;
            @JsonProperty("app_name")
            private String appName;
            @JsonProperty("type")
            private String type;
            @JsonProperty("reason")
            private String reason;

            public String getPoints() {
                return points;
            }

            public String getAppName() {
                return appName;
            }

            public String getReason() {
                return reason;
            }

            public String getTime() {
                return time;
            }
        }
    }
}
