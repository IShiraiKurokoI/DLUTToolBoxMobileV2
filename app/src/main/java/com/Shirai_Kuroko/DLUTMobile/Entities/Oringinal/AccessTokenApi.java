package com.Shirai_Kuroko.DLUTMobile.Entities.Oringinal;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class AccessTokenApi implements Serializable {
    @JsonProperty("app_key")
    private String appKey = "20460cbb2ccf1c97";

    @JsonProperty("app_secret")
    private String appSecret = "1dcc14a227a6f8d9b37792b7b053f671";

    @JsonProperty("grant_type")
    private String grantType = "client_credentials";

    @JsonProperty("open_token_api")
    private String openTokenApi = "https://api.m.dlut.edu.cn/oauth/token";

    @JsonProperty("scope")
    private String scope = "all";

    public String getAppKey() {
        return this.appKey;
    }

    public String getAppSecret() {
        return this.appSecret;
    }

    public String getGrantType() {
        return this.grantType;
    }

    public String getOpenTokenApi() {
        return this.openTokenApi;
    }

    public String getScope() {
        return this.scope;
    }
}
