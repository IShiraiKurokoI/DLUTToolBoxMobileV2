package com.Shirai_Kuroko.DLUTMobile.Entities.Oringinal;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class AccessTokenApi implements Serializable {

    public String getAppKey() {
        String appKey = "20460cbb2ccf1c97";
        return appKey;
    }

    public String getAppSecret() {
        String appSecret = "1dcc14a227a6f8d9b37792b7b053f671";
        return appSecret;
    }

    public String getGrantType() {
        String grantType = "client_credentials";
        return grantType;
    }

    public String getOpenTokenApi() {
        String openTokenApi = "https://api.m.dlut.edu.cn/oauth/token";
        return openTokenApi;
    }

    public String getScope() {
        String scope = "all";
        return scope;
    }
}
