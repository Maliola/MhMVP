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

package com.lib.socialize.share.core.handler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.lib.socialize.share.core.LibShareConfiguration;
import com.lib.socialize.share.core.OnAuthListener;
import com.lib.socialize.share.core.TokenInfo;
import com.tencent.open.utils.ThreadManager;

/**
 */
public abstract class AbsLoginHandler implements ILoginHandler {
    public static final  int OPENTYPE_QQ =0 ;
    public static final  int OPENTYPE_WEIBO=1 ;
    public static final  int OPENTYPE_WECHAT =2 ;
    protected Context mContext;
    private OnAuthListener mShareListener;
    private TokenInfo authInfo;
    protected LibShareConfiguration mShareConfiguration;
    public AbsLoginHandler(Activity context,LibShareConfiguration configuration) {
        initContext(context);
        mShareConfiguration = configuration;
    }

    private void initContext(Activity context) {
        if (isNeedActivityContext()) {
            mContext = context;
        } else {
            mContext = context.getApplicationContext();
        }
    }

    protected boolean isNeedActivityContext() {
        return false;
    }


    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState, OnAuthListener listener) {
        initContext(activity);
    }

    @Override
    public void onActivityNewIntent(Activity activity, Intent intent) {
        initContext(activity);
    }

    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data, OnAuthListener listener) {
        initContext(activity);
    }

    @Override
    public void onActivityDestroy() {
        release();
    }

    @Override
    public void release() {
        mShareListener = null;
        mContext = null;
    }

    @Override
    public void login(OnAuthListener onAuthListener) throws Exception {
        mShareListener = onAuthListener;
        checkConfig();
        init();
    }

    protected void doOnWorkThread(final Runnable runnable) {
        mShareConfiguration.getTaskExecutor().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    runnable.run();
                } catch (Exception e) {
                    e.printStackTrace();

                    if (getAuthListener() != null) {
                        doOnMainThread(new Runnable() {
                            @Override
                            public void run() {
                                getAuthListener().onAuthFaild();
                            }
                        });
                    }
                }
            }
        });
    }

    protected void doOnMainThread(Runnable runnable) {
        ThreadManager.getMainHandler().post(runnable);
    }



    public void setOnAuthListener(OnAuthListener onAuthListener) {
        mShareListener = onAuthListener;
    }

    public OnAuthListener getAuthListener() {
        return mShareListener;
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    public TokenInfo getAuthInfo() {
        return authInfo;
    }

    /**
     * 检查配置，比如appKey，appSecret
     */
    protected abstract void checkConfig() throws Exception;

    protected abstract void init() throws Exception;
}
