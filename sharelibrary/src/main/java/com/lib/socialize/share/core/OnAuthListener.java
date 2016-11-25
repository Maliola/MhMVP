package com.lib.socialize.share.core;

/**
 * Created by stone on 16/8/10.
 */
public interface OnAuthListener {
    void onAuthFaild();
    void onAuthSuccess(TokenInfo tokenInfo);
}
