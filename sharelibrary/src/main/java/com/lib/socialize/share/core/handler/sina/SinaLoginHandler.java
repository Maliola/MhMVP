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

package com.lib.socialize.share.core.handler.sina;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.lib.socialize.share.core.LibShareConfiguration;
import com.lib.socialize.share.core.OnAuthListener;
import com.lib.socialize.share.core.SharePlatformConfig;
import com.lib.socialize.share.core.SocializeMedia;
import com.lib.socialize.share.core.TokenInfo;
import com.lib.socialize.share.core.error.ShareConfigException;
import com.lib.socialize.share.core.handler.AbsLoginHandler;
import com.lib.socialize.share.core.helper.AccessTokenKeeper;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;

import java.util.Map;


public class SinaLoginHandler extends AbsLoginHandler {

    private static String mAppKey;
    public static final String DEFAULT_REDIRECT_URL = "https://api.weibo.com/oauth2/default.html";
    public static final String DEFAULT_SCOPE = "email,direct_messages_read,direct_messages_write,"
            + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
            + "follow_app_official_microblog," + "invitation_write";

    private static SsoHandler mSsoHandler;

    public SinaLoginHandler(Activity context, LibShareConfiguration configuration) {
        super(context, configuration);
    }

    @Override
    public void checkConfig() throws Exception {
        if (!TextUtils.isEmpty(mAppKey)) {
            return;
        }

        Map<String, Object> appConfig = SharePlatformConfig.getPlatformDevInfo(SocializeMedia.SINA);
        if (appConfig == null || appConfig.isEmpty() || TextUtils.isEmpty(mAppKey = (String) appConfig.get(SharePlatformConfig.APP_KEY))) {
            throw new ShareConfigException("Please set Sina platform dev info.");
        }
    }

    @Override
    public void init() throws Exception {
        //安装客户端后要重新创建实例
        AuthInfo mAuthInfo = new AuthInfo(getContext(), mAppKey, mShareConfiguration.getSinaRedirectUrl(), mShareConfiguration.getSinaScope());
        mSsoHandler = new SsoHandler((Activity) getContext(), mAuthInfo);
        mSsoHandler.authorize(mAuthListener);

    }

    @Override
    public void login(OnAuthListener onAuthListener) throws Exception {
        super.login(onAuthListener);
    }

    /**
     * 当 Activity 被重新初始化时（该 Activity 处于后台时，可能会由于内存不足被杀掉了），
     * 需要调用 {@link IWeiboShareAPI#handleWeiboResponse} 来接收微博客户端返回的数据。
     * 执行成功，返回 true，并调用 {@link IWeiboHandler.Response#onResponse}；
     * 失败返回 false，不调用上述回调
     *
     * @param activity
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState, OnAuthListener listener) {
        super.onActivityCreated(activity, savedInstanceState, listener);

    }

    /**
     * 从当前应用唤起微博并进行分享后，返回到当前应用时，需要在此处调用该函数
     * 来接收微博客户端返回的数据；执行成功，返回 true，并调用
     * {@link IWeiboHandler.Response#onResponse}；失败返回 false，不调用上述回调
     *
     * @param intent
     */
    @Override
    public void onActivityNewIntent(Activity activity, Intent intent) {
        super.onActivityNewIntent(activity, intent);

    }

    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data, OnAuthListener listener) {
        super.onActivityResult(activity, requestCode, resultCode, data, listener);
        if (mSsoHandler != null && TextUtils.isEmpty(getToken())) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }

    }


    private WeiboAuthListener mAuthListener = new WeiboAuthListener() {

        @Override
        public void onWeiboException(WeiboException arg0) {
            if (getAuthListener() != null) {
                getAuthListener().onAuthFaild();
            }        }

        @Override
        public void onComplete(Bundle bundle) {
            String token = bundle.getString("access_token");
            String expires_in = bundle.getString("expires_in");
            String oauthUid = bundle.getString("uid");

            OnAuthListener listener = getAuthListener();
            if (listener == null) {
                return;
            }
            TokenInfo tokenInfo = new TokenInfo(OPENTYPE_WEIBO,oauthUid,token,"",mShareConfiguration.getSinaScope());
            getAuthListener().onAuthSuccess(tokenInfo);
        }

        @Override
        public void onCancel() {
            if (getAuthListener() != null) {
                getAuthListener().onAuthFaild();
            }
        }
    };

//    public void onResponse(BaseResponse baseResp) {
//        OnAuthListener listener = getAuthListener();
//        if (listener == null) {
//            return;
//        }
//
//        switch (baseResp.errCode) {
//            case WBConstants.ErrorCode.ERR_OK:
//                //listener.onSuccess(SocializeMedia.SINA, ShareStatusCode.ST_CODE_SUCCESSED);
//                break;
//            case WBConstants.ErrorCode.ERR_CANCEL:
//                //listener.onCancel(SocializeMedia.SINA);
//                break;
//            case WBConstants.ErrorCode.ERR_FAIL:
//                //listener.onError(SocializeMedia.SINA, ShareStatusCode.ST_CODE_SHARE_ERROR_SHARE_FAILED, new ShareException(baseResp.errMsg));
//                break;
//        }
//    }

    private String getToken() {
        Oauth2AccessToken mAccessToken = AccessTokenKeeper.readAccessToken(getContext());
        String token = null;
        if (mAccessToken != null) {
            token = mAccessToken.getToken();
        }
        return token;
    }



    @Override
    protected boolean isNeedActivityContext() {
        return true;
    }

    @Override
    public SocializeMedia getShareMedia() {
        return SocializeMedia.SINA;
    }

}
