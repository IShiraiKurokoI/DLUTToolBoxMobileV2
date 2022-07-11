package com.Shirai_Kuroko.DLUTMobile.Entities.Oringinal;

import java.io.*;

public class DLUTNoticeContentBean implements Serializable
{
    private String description;
    private String picurl;
    private String schema;
    private String title;
    private String url;

    public DLUTNoticeContentBean() {
        super();
    }

    public String getDescription() {
        return this.description;
    }

    public String getPicurl() {
        return this.picurl;
    }

    public String getSchema() {
        return this.schema;
    }

    public String getTitle() {
        return this.title;
    }

    public String getUrl() {
        return this.url;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setPicurl(final String picurl) {
        this.picurl = picurl;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public void setUrl(final String url) {
        this.url = url;
    }
}
