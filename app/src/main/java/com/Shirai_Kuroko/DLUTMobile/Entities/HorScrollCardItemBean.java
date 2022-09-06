package com.Shirai_Kuroko.DLUTMobile.Entities;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Shirai_Kuroko
 * @version 1.0.0
 * @ClassName HorScrollCardItemBean.java
 * @Description TODO
 * @createTime 2022年09月06日 08:29
 */


@Data
@NoArgsConstructor
public class HorScrollCardItemBean
{
    private String image;
    private String subtitle;
    private String title;
    private String url;

    public String getImage() {
        return this.image;
    }

    public String getSubtitle() {
        return this.subtitle;
    }

    public String getTitle() {
        return this.title;
    }

    public String getUrl() {
        return this.url;
    }

    public void setImage(final String image) {
        this.image = image;
    }

    public void setSubtitle(final String subtitle) {
        this.subtitle = subtitle;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public void setUrl(final String url) {
        this.url = url;
    }
}

