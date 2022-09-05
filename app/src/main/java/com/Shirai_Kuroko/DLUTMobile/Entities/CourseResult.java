package com.Shirai_Kuroko.DLUTMobile.Entities;

import com.Shirai_Kuroko.DLUTMobile.Entities.Oringinal.CourseBean;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CourseResult {

    @JsonProperty("errcode")
    private Integer errcode;
    @JsonProperty("errmsg")
    private String errmsg;
    @JsonProperty("data")
    private List<CourseBean> data;

    public String getErrmsg() {
        return errmsg;
    }

    public Integer getErrcode() {
        return errcode;
    }

    public List<CourseBean> getData() {
        return data;
    }
}
