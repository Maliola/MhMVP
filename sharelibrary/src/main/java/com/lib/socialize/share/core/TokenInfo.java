package com.lib.socialize.share.core;

/**
 * Created by stone on 16/8/8.
 */
public class TokenInfo {

    int openType;
    String openId;
    String accessToken;
    String refreshToken;
    String scope;
    public TokenInfo(int openType, String openId, String accessToken, String refreshToken, String scope){
        this.openId = openId;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.openType = openType;
        this.scope = scope;
    }
    public int getOpenType() {
        return openType;
    }

    public void setOpenType(int openType) {
        this.openType = openType;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
