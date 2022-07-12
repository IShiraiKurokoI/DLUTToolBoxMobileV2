package com.Shirai_Kuroko.DLUTMobile.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class RankResult
{
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
        @JsonProperty("list")
        private List<ListDTO> list;

        public List<ListDTO> getList() {
            return list;
        }

        @NoArgsConstructor
        @Data
        public static class ListDTO {
            @JsonProperty("points")
            private String points;
            @JsonProperty("user_id")
            private String userId;
            @JsonProperty("head")
            private String head;
            @JsonProperty("sex")
            private String sex;
            @JsonProperty("name")
            private String name;
            @JsonProperty("org_name")
            private String orgName;

            public String getName() {
                return name;
            }

            public String getHead() {
                return head;
            }

            public String getOrgName() {
                return orgName;
            }

            public String getPoints() {
                return points;
            }
        }
    }
}
