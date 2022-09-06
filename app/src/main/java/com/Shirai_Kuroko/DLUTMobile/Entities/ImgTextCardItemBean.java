package com.Shirai_Kuroko.DLUTMobile.Entities;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Shirai_Kuroko
 * @version 1.0.0
 * @ClassName ImgTextCardItemBean.java
 * @Description TODO
 * @createTime 2022年09月06日 08:50
 */


@Data
@NoArgsConstructor
public class ImgTextCardItemBean
{
    private String content;
    private String image;
    private String title;
    private String url;

    public String getContent() {
        return this.content;
    }

    public String getImage() {
        return this.image;
    }

    public String getTitle() {
        return this.title;
    }

    public String getUrl() {
        return this.url;
    }

    public void setContent(final String content) {
        this.content = content;
    }

    public void setImage(final String image) {
        this.image = image;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public void setUrl(final String url) {
        this.url = url;
    }
}

