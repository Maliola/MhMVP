/*
 * Copyright (C) 2015 Bilibili <jungly.ik@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lib.socialize.share.core.handler.qq;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.lib.socialize.share.core.LibShareConfiguration;
import com.lib.socialize.share.core.OnAuthListener;
import com.lib.socialize.share.core.SharePlatformConfig;
import com.lib.socialize.share.core.SocializeMedia;
import com.lib.socialize.share.core.TokenInfo;
import com.lib.socialize.share.core.error.ShareConfigException;
import com.lib.socialize.share.core.handler.AbsLoginHandler;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 */
public  class QQLoginHandler extends AbsLoginHandler {

    private static String mAppId;
    protected static Tencent mTencent;
    public QQLoginHandler(Activity context, LibShareConfiguration configuration) {
        super(context, configuration);
    }

    private static Map<String, Object> getAppConfig() {
        Map<String, Object> appConfig = SharePlatformConfig.getPlatformDevInfo(SocializeMedia.QQ);
        if (appConfig == null || appConfig.isEmpty()) {
            appConfig = SharePlatformConfig.getPlatformDevInfo(SocializeMedia.QZONE);
        }

        return appConfig;
    }



    @Override
    protected void checkConfig() throws Exception {
        if (!TextUtils.isEmpty(mAppId)) {
            return;
        }

        Map<String, Object> appConfig = getAppConfig();
        if (appConfig == null || appConfig.isEmpty()
                || TextUtils.isEmpty(mAppId = (String) appConfig.get(SharePlatformConfig.APP_ID))) {
            throw new ShareConfigException("Please set QQ platform dev info.");
        }
    }

    @Override
    protected void init() throws Exception {
        if (mTencent == null) {
            mTencent = Tencent.createInstance(mAppId, getContext());
        }
        if (!mTencent.isSessionValid()) {
            mTencent.login((Activity) getContext(), mShareConfiguration.getQQScope(),mUiListener);
        } else {
            mTencent.logout(getContext());
        }
    }

    @Override
    protected boolean isNeedActivityContext() {
        return true;
    }

    protected final IUiListener mUiListener = new IUiListener() {
        @Override
        public void onCancel() {
            if (getAuthListener() != null) {
                getAuthListener().onAuthFaild();
            }
        }

        @Override
        public void onComplete(Object response) {
            if (getAuthListener() != null) {
                JSONObject values = (JSONObject) response;
                String openid = "";
                String access_token = "";
                String expires_in = "";
                try {
                    if (values.has("openid")) {
                        openid = values.getString("openid");
                    }
                    if (values.has("access_token")) {
                        access_token = values.getString("access_token");
                    }
                    if (values.has("expires_in")) {
                        expires_in = values.getString("expires_in");
                    }
                   // getQQUserInfo(openid,access_token,expires_in);
                    TokenInfo tokenInfo = new TokenInfo(OPENTYPE_QQ,openid,access_token,"",mShareConfiguration.getQQScope());
                    getAuthListener().onAuthSuccess(tokenInfo);
                } catch (JSONException e) {
                    e.printStackTrace();
                    if (getAuthListener() != null) {
                        getAuthListener().onAuthFaild();
                    }
                }
            }
        }

        @Override
        public void onError(UiError e) {
            if (getAuthListener() != null) {
                getAuthListener().onAuthFaild();
            }
        }
    };

    @Override
    public void release() {
        super.release();
        mTencent = null;
    }

    @Override
    public SocializeMedia getShareMedia() {
        return SocializeMedia.QQ;
    }
    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data, OnAuthListener listener) {
        Log.e("QQLoginHandler","onActivityResult--requestCode---"+requestCode);
        super.onActivityResult(activity, requestCode, resultCode, data, listener);
        if (requestCode == Constants.REQUEST_LOGIN ||
                requestCode == Constants.REQUEST_APPBAR) {
            Tencent.onActivityResultData(requestCode,resultCode,data,mUiListener);
        }
    }

    /**
     * 获取 qq用户信息
     */
//    private void getQQUserInfo(String openid,String access_token,String expires_in) {
//
//            mTencent.setOpenId(openid);
//            mTencent.setAccessToken(access_token, expires_in);
//            IUiListener listener = new IUiListener() {
//
//                @Override
//                public void onError(UiError e) {
//
//                }
//
//                @Override
//                public void onComplete(final Object response) {
//
//                }
//
//                @Override
//                public void onCancel() {
//                    LogUtil.i("getuserinfo", "onCancel");
//                }
//            };
//            UserInfo  mInfo = new UserInfo(getContext(), mTencent.getQQToken());
//            mInfo.getUserInfo(listener);
//
//        }

}
