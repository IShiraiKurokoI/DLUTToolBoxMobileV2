package com.Shirai_Kuroko.DLUTMobile.Entities;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Shirai_Kuroko
 * @version 1.0.0
 * @ClassName TextCardItemBean.java
 * @Description TODO
 * @createTime 2022年09月06日 08:59
 */


@Data
@NoArgsConstructor
public class TextCardItemBean {
    private String text;
    private long time;
    private String title;
    private int unread;
    private String url;

    public String getText() {
        return this.text;
    }

    public long getTime() {
        return this.time * 1000L;
    }

    public String getTitle() {
        return this.title;
    }

    public String getUrl() {
        return this.url;
    }

    public boolean isRead() {
        return this.unread != 0;
    }

    public void setTime(final long time) {
        this.time = time;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public void setUrl(final String url) {
        this.url = url;
    }
}