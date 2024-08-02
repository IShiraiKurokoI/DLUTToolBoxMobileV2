package com.Shirai_Kuroko.DLUTMobile.Entities.Oringinal;

import java.io.Serializable;

public class AccessTokenApi implements Serializable {

    public String getAppKey() {
        return "20460cbb2ccf1c97";
    }

    public String getAppSecret() {
        return "1dcc14a227a6f8d9b37792b7b053f671";
    }

    public String getGrantType() {
        return "client_credentials";
    }

    public String getOpenTokenApi() {
        return "https://api.m.dlut.edu.cn/oauth/token";
    }

    public String getScope() {
        return "all";
    }
}
