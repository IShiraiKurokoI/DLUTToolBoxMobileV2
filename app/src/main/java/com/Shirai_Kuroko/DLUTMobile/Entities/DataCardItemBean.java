package com.Shirai_Kuroko.DLUTMobile.Entities;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Shirai_Kuroko
 * @version 1.0.0
 * @ClassName DataCardItemBean.java
 * @Description TODO
 * @createTime 2022年09月06日 08:02
 */

@Data
@NoArgsConstructor
public class DataCardItemBean
{
    private String color;
    private String title;
    private String value;

    public String getColor() {
        return this.color;
    }

    public String getTitle() {
        return this.title;
    }

    public String getValue() {
        return this.value;
    }

    public void setColor(final String color) {
        this.color = color;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public void setValue(final String value) {
        this.value = value;
    }
}
