package com.Shirai_Kuroko.DLUTMobile.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Shirai_Kuroko
 * @version 1.0.0
 * @ClassName ScoreFunResultbean.java
 * @Description TODO
 * @createTime 2022年10月06日 21:55
 */
@NoArgsConstructor
@Data
public class ScoreFunResultbean {

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

    public Integer getRet() {
        return ret;
    }

    public Integer getErrcode() {
        return errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    @NoArgsConstructor
    @Data
    public static class DataDTO {
        @JsonProperty("id")
        private String id;
        @JsonProperty("sum")
        private String sum;
        @JsonProperty("add_score")
        private Integer add_score;

        public String getId() {
            return id;
        }

        public String getSum() {
            return sum;
        }

        public Integer getAdd_score() {
            return add_score;
        }
    }

}
