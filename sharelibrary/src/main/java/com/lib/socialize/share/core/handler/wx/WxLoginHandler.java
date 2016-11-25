package com.lib.socialize.share.core.handler.wx;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.lib.socialize.share.R;
import com.lib.socialize.share.core.LibShareConfiguration;
import com.lib.socialize.share.core.OnAuthListener;
import com.lib.socialize.share.core.SharePlatformConfig;
import com.lib.socialize.share.core.SocializeMedia;
import com.lib.socialize.share.core.TokenInfo;
import com.lib.socialize.share.core.error.ShareConfigException;
import com.lib.socialize.share.core.error.ShareException;
import com.lib.socialize.share.core.error.ShareStatusCode;
import com.lib.socialize.share.core.handler.AbsLoginHandler;
import com.lib.socialize.share.util.HttpUtils;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.json.JSONObject;

import java.text.MessageFormat;
import java.util.Map;

/**
 * Created by stone on 16/8/8.
 */
public class WxLoginHandler extends AbsLoginHandler {


    private static String mAppId;
    private static String AppSecret;
    public static IWXAPI mIWXAPI;
    private ResultHolder mResultHolder = new ResultHolder();

    public WxLoginHandler(Activity context, LibShareConfiguration configuration) {
        super(context, configuration);
    }

    private static Map<String, Object> getAppConfig() {
        Map<String, Object> appConfig = SharePlatformConfig.getPlatformDevInfo(SocializeMedia.WEIXIN);
        if (appConfig == null || appConfig.isEmpty()) {
            appConfig = SharePlatformConfig.getPlatformDevInfo(SocializeMedia.WEIXIN_MONMENT);
        }
        return appConfig;
    }

    @Override
    protected void checkConfig() throws Exception {
        if (!TextUtils.isEmpty(mAppId)) {
            return;
        }

        Map<String, Object> appConfig = getAppConfig();
        if (appConfig == null || appConfig.isEmpty() ||
                TextUtils.isEmpty(mAppId = (String) appConfig.get(SharePlatformConfig.APP_ID))||TextUtils.isEmpty(AppSecret = (String) appConfig.get(SharePlatformConfig.APP_SECRET))) {
            throw new ShareConfigException("Please set WeChat platform dev info.");
        }
    }

    @Override
    protected void init() throws Exception {
        if (mIWXAPI == null) {
            mIWXAPI = WXAPIFactory.createWXAPI(getContext(), mAppId, true);
            if (mIWXAPI.isWXAppInstalled()) {
                mIWXAPI.registerApp(mAppId);
            }
        }

        if (!mIWXAPI.isWXAppInstalled()) {
            String msg = getContext().getString(R.string.bili_share_sdk_not_install_wechat);
            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
            throw new ShareException(msg, ShareStatusCode.ST_CODE_SHARE_ERROR_NOT_INSTALL);
        }
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState, OnAuthListener listener) {
        super.onActivityCreated(activity, savedInstanceState, listener);
        if (mResultHolder.mResp != null) {
            parseResult(mResultHolder.mResp, listener);
            mResultHolder.mResp = null;
        }
    }

    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data, OnAuthListener listener) {
        super.onActivityResult(activity, requestCode, resultCode, data, listener);
        if (mResultHolder.mResp != null) {
            parseResult(mResultHolder.mResp, listener);
            mResultHolder.mResp = null;
        }
    }


    @Override
    public void login(OnAuthListener onAuthListener) throws Exception {
        super.login(onAuthListener);
        SendAuth.Req req = new SendAuth.Req();
        req.scope = mShareConfiguration.getWeiXinScope();
        req.state = "wechat_sdk_demo_test";
        mIWXAPI.sendReq(req);
    }

    public void onReq(BaseReq baseReq) {

    }

    public void onResp(BaseResp resp) {
        OnAuthListener listener = getAuthListener();
        if (listener == null) {
            mResultHolder.mResp = resp;
            return;
        }

        parseResult(resp, listener);
    }

    private void parseResult(BaseResp resp, OnAuthListener listener) {
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                if(resp instanceof SendAuth.Resp){
                   String code = ((SendAuth.Resp) resp).code;
                    getWXToken(code);
                }
                break;

            case BaseResp.ErrCode.ERR_USER_CANCEL:
                if(getAuthListener()!=null){
                    getAuthListener().onAuthFaild();
                }
                break;

            case BaseResp.ErrCode.ERR_SENT_FAILED:
                if(getAuthListener()!=null){
                    getAuthListener().onAuthFaild();
                }
                break;
        }
    }



    private static class ResultHolder {
        BaseResp mResp;
    }

    @Override
    public SocializeMedia getShareMedia() {
        return SocializeMedia.WEIXIN;
    }

    /**
     * {
     "access_token":"ACCESS_TOKEN",
     "expires_in":7200,
     "refresh_token":"REFRESH_TOKEN",
     "openid":"OPENID",
     "scope":"SCOPE"
     }
     {"openid":"omrpxwbCBNUpFoF6zpKmm_KGv_KM","nickname":"张磊","sex":1,"language":"zh_CN","city":"","province":"Beijing","country":"CN","headimgurl":"http:\/\/wx.qlogo.cn\/mmopen\/CkaetofTzQSussJkdPnQAoUBhsXt1P86wFN6um3HX4ZZUjomFYG9IjRkX6BUk8kULWKZ5X6a1CoxZp8TcSNP2AAya8Cmp3ot\/0","privilege":[],"unionid":"ooigev7F_vqSwc9rYJIBkTAWaIxk"}
     * https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code
     */
    private void getWXToken(final String code){
        doOnWorkThread(new Runnable() {
            @Override
            public void run() {
                try{
                    String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid={0}&secret={1}&code={2}&grant_type=authorization_code";
                    String realUrl = MessageFormat.format(url,mAppId,AppSecret,code);
                    String content = HttpUtils.httpGet(realUrl);
                    if(!TextUtils.isEmpty(content)) {
                        JSONObject jsonObject = new JSONObject(content);
                       final  String access_token = jsonObject.getString("access_token");
                        final String refresh_token = jsonObject.getString("refresh_token");
                       final  String openid = jsonObject.getString("openid");
                        Log.e("WxLoginHandler", "getWXToken----" + content);
                        doOnMainThread(new Runnable() {
                            @Override
                            public void run() {
                                TokenInfo tokenInfo = new TokenInfo(OPENTYPE_WECHAT,openid,access_token,refresh_token,mShareConfiguration.getWeiXinScope());
                                getAuthListener().onAuthSuccess(tokenInfo);
                            }
                        });
                    }
                }catch (Exception e){
                   if(getAuthListener()!=null){
                       getAuthListener().onAuthFaild();
                   }
                }

            }
        });
    }

}
