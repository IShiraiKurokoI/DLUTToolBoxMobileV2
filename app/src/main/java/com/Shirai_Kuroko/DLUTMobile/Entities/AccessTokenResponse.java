package com.Shirai_Kuroko.DLUTMobile.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class AccessTokenResponse {

    @JsonProperty("access_token")
    private String access_token;
    @JsonProperty("expires_in")
    private Integer expires_in;
    @JsonProperty("scope")
    private String scope;
    @JsonProperty("refresh_token")
    private String refresh_token;
    @JsonProperty("token_type")
    private String token_type;

    public String getAccess_token() {
        return access_token;
    }
}
