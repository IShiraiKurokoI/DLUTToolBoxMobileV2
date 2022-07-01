package com.Shirai_Kuroko.DLUTMobile.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class GridAppID {

    @JsonProperty("id")
    private Integer id;

    public Integer getId() {
        return id;
    }

    public GridAppID(int _id)
    {
        this.id = _id;
    }
}
