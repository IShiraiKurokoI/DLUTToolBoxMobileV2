package com.Shirai_Kuroko.DLUTMobile.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ExchangeRecordResult {

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
            @JsonProperty("create_time")
            private long create_time;
            @JsonProperty("end_time")
            private long end_time;
            @JsonProperty("exchange_time")
            private long exchange_time;
            @JsonProperty("id")
            private String id;
            @JsonProperty("image")
            private List<String> image;
            @JsonProperty("is_exchange")
            private int is_exchange;
            @JsonProperty("name")
            private String name;
            @JsonProperty("points")
            private String points;
            @JsonProperty("present_id")
            private String present_id;
            @JsonProperty("type")
            private int type;

            public String getId() {
                return this.id;
            }

            public List<String> getImage() {
                return this.image;
            }

            public int getIs_exchange() {
                return this.is_exchange;
            }

            public String getName() {
                return this.name;
            }

            public String getPoints() {
                return this.points;
            }

            public String getPresent_id() {
                return this.present_id;
            }

            public int getType() {
                return this.type;
            }

            public long getCreate_time() {
                return create_time;
            }

            public long getEnd_time() {
                return end_time;
            }

            public long getExchange_time() {
                return exchange_time;
            }
        }
    }
}
