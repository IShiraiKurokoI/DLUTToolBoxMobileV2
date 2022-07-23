package com.Shirai_Kuroko.DLUTMobile.Entities.Oringinal;

public class FileResponseBean
{
    private int error;
    private String file_uri;
    private String file_url;
    private String uri;
    private String url;

    public FileResponseBean() {
        super();
    }

    public int getError() {
        return this.error;
    }

    public String getFile_uri() {
        return this.file_uri;
    }

    public String getFile_url() {
        return this.file_url;
    }

    public String getUri() {
        return this.uri;
    }

    public String getUrl() {
        return this.url;
    }

    public void setError(final int error) {
        this.error = error;
    }

    public void setFile_uri(final String file_uri) {
        this.file_uri = file_uri;
    }

    public void setFile_url(final String file_url) {
        this.file_url = file_url;
    }

    public void setUri(final String uri) {
        this.uri = uri;
    }

    public void setUrl(final String url) {
        this.url = url;
    }
}
