package com.Shirai_Kuroko.DLUTMobile.Entities;

@lombok.NoArgsConstructor
@lombok.Data
public class UserScoreBean {
    @com.fasterxml.jackson.annotation.JsonProperty("data")
    private DataDTO data;
    @com.fasterxml.jackson.annotation.JsonProperty("ret")
    private Integer ret;
    @com.fasterxml.jackson.annotation.JsonProperty("errcode")
    private Integer errcode;
    @com.fasterxml.jackson.annotation.JsonProperty("errmsg")
    private String errmsg;

    public DataDTO getData() {
        return data;
    }

    @lombok.NoArgsConstructor
    @lombok.Data
    public static class DataDTO
    {
        @com.fasterxml.jackson.annotation.JsonProperty("status")
        private String status;
        @com.fasterxml.jackson.annotation.JsonProperty("user_points")
        private Integer user_points;
        @com.fasterxml.jackson.annotation.JsonProperty("rank")
        private String rank;
        @com.fasterxml.jackson.annotation.JsonProperty("in_points")
        private String in_points;
        @com.fasterxml.jackson.annotation.JsonProperty("out_points")
        private String out_points;
        @com.fasterxml.jackson.annotation.JsonProperty("lottery_time")
        private Lottery_Time lottery_time;

        public Integer getUser_points() {
            return user_points;
        }

        public String getIn_points() {
            return in_points;
        }

        public String getOut_points() {
            return out_points;
        }

        public String getRank() {
            return rank;
        }

        @lombok.NoArgsConstructor
        @lombok.Data
        public static class Lottery_Time{}
    }
}
