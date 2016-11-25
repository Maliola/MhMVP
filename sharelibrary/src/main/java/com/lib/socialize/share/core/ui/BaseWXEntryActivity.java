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

package com.lib.socialize.share.core.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.lib.socialize.share.core.LibShareConfiguration;
import com.lib.socialize.share.core.SocializeMedia;
import com.lib.socialize.share.core.handler.IShareHandler;
import com.lib.socialize.share.core.handler.LoginHandlerPool;
import com.lib.socialize.share.core.handler.ShareHandlerPool;
import com.lib.socialize.share.core.handler.wx.BaseWxShareHandler;
import com.lib.socialize.share.core.handler.wx.WxChatShareHandler;
import com.lib.socialize.share.core.handler.wx.WxLoginHandler;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**

 */
public abstract class BaseWXEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI mIWXAPI;
    private BaseWxShareHandler mShareHandler;
    private WxLoginHandler wxLoginHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        IShareHandler wxHandler = ShareHandlerPool.getCurrentHandler(SocializeMedia.WEIXIN);
        if (wxHandler == null) {
            wxHandler = ShareHandlerPool.getCurrentHandler(SocializeMedia.WEIXIN_MONMENT);
        }
        if (wxHandler == null) {
            wxHandler = new WxChatShareHandler(this, new LibShareConfiguration.Builder(this).build());
        }
        wxLoginHandler = (WxLoginHandler)LoginHandlerPool.getCurrentHandler(SocializeMedia.WEIXIN);

        if(wxLoginHandler==null){
            wxLoginHandler = new WxLoginHandler(this,new LibShareConfiguration.Builder(this).build());
        }

        mShareHandler = (BaseWxShareHandler) wxHandler;

        if (isAutoCreateWXAPI()) {
            mIWXAPI = WXAPIFactory.createWXAPI(this, getAppId(), true);
            if (mIWXAPI.isWXAppInstalled()) {
                mIWXAPI.registerApp(getAppId());
            }
            mIWXAPI.handleIntent(getIntent(), this);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        setIntent(intent);
        if (mIWXAPI != null) {
            mIWXAPI.handleIntent(intent, this);
        }
    }

    @Override
    public void onReq(BaseReq baseReq) {
        if (mShareHandler != null) {
            mShareHandler.onReq(baseReq);
        }
        if(wxLoginHandler!=null){
            wxLoginHandler.onReq(baseReq);
        }
        if (isAutoFinishAfterOnReq()) {
            finish();
        }
    }

    @Override
    public void onResp(BaseResp resp) {

        if (resp instanceof SendAuth.Resp) {
            if(wxLoginHandler!=null){
                wxLoginHandler.onResp(resp);
            }
        }else{
            if (mShareHandler != null) {
                mShareHandler.onResp(resp);
            }
        }

        if (isAutoFinishAfterOnResp()) {
            finish();
        }
    }

    protected boolean isAutoFinishAfterOnReq() {
        return true;
    }

    protected boolean isAutoFinishAfterOnResp() {
        return true;
    }

    protected boolean isAutoCreateWXAPI() {
        return true;
    }

    protected abstract String getAppId();

}
