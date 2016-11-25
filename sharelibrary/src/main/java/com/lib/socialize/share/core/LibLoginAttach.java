package com.lib.socialize.share.core;

import android.app.Activity;
import android.content.Intent;

import com.lib.socialize.share.core.error.ShareException;
import com.lib.socialize.share.core.handler.ILoginHandler;
import com.lib.socialize.share.core.handler.LoginHandlerPool;

/**
 * Created by stone on 16/8/8.
 */
public class LibLoginAttach {

    private ILoginHandler mCurrentHandler;

    private LibShareConfiguration mShareConfiguration;

    private SocializeMedia type;

    private OnAuthListener listener;

    public void init(LibShareConfiguration configuration) {
        mShareConfiguration = configuration;
    }

    public void login(Activity activity, SocializeMedia type, OnAuthListener listener) {
        this.listener = listener;
        this.type = type;
        if (mShareConfiguration == null) {
            throw new IllegalArgumentException("ShareConfiguration must be initialized before share");
        }
        if (mCurrentHandler != null) {
            release(mCurrentHandler.getShareMedia());
        }
        mCurrentHandler = LoginHandlerPool.newHandler(activity, type, mShareConfiguration);
        if (mCurrentHandler != null) {
            try {
                mCurrentHandler.login(listener);
            } catch (ShareException e) {
                e.printStackTrace();
                listener.onAuthFaild();
            } catch (Exception e) {
                listener.onAuthFaild();
                e.printStackTrace();
            }
        }
    }


    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
        if (mCurrentHandler != null) {
            mCurrentHandler.onActivityResult(activity, requestCode, resultCode, data, listener);
        }
    }


    public void onActivityDestroy() {
        if (mCurrentHandler != null) {
            mCurrentHandler.onActivityDestroy();
        }
    }

    private void release(SocializeMedia type) {
        if (mCurrentHandler != null) {
            mCurrentHandler.release();
        }
        LoginHandlerPool.remove(type);
        LoginHandlerPool.release();
        mCurrentHandler = null;
    }

    public LibShareConfiguration getShareConfiguration() {
        return mShareConfiguration;
    }


    public void release() {
        release(type);
    }
}
