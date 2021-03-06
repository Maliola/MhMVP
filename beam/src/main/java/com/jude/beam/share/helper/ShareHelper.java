/*
 * Copyright (c) 2015. BiliBili Inc.
 */

package com.jude.beam.share.helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.lib.socialize.share.core.SocializeUtils;
import com.lib.socialize.share.core.SocializeListeners;
import com.lib.socialize.share.core.SocializeMedia;
import com.lib.socialize.share.core.shareparam.BaseShareParam;
import com.lib.socialize.share.util.selector.BaseSharePlatformSelector;
import com.lib.socialize.share.util.selector.BaseSharePlatformSelector.ShareTarget;
import com.lib.socialize.share.util.selector.DialogSharePlatformSelector;
import com.lib.socialize.share.util.selector.PopFullScreenSharePlatformSelector;
import com.lib.socialize.share.util.selector.PopWrapSharePlatformSelector;

/**
 * Helper
 *
 * .
 */
public final class ShareHelper {

    private Activity mContext;
    private Callback mCallback;
    private BaseSharePlatformSelector mPlatformSelector;

    public static ShareHelper instance(Activity context, Callback callback) {
        return new ShareHelper(context, callback);
    }

    private ShareHelper(Activity context, Callback callback) {
        mContext = context;
        mCallback = callback;
        if (context == null) {
            throw new NullPointerException();
        }
    }

    public void onActivityResult(Activity context, int requestCode, int resultCode, Intent data) {
            SocializeUtils.onActivityResult(context, requestCode, resultCode, data);
    }

    public void onActivityDestroy(){
        SocializeUtils.onActivityDestroy();
        reset();
    }

    public void setCallback(Callback mCallback) {
        this.mCallback = mCallback;
    }

    public void showShareDialog() {
        mPlatformSelector = new DialogSharePlatformSelector(mContext, new BaseSharePlatformSelector.OnShareSelectorDismissListener() {
            @Override
            public void onDismiss() {
                onShareSelectorDismiss();
            }
        }, mShareItemClick);
        mPlatformSelector.show();
    }

    public void showShareWarpWindow(View anchor) {
        mPlatformSelector = new PopWrapSharePlatformSelector(mContext, anchor, new BaseSharePlatformSelector.OnShareSelectorDismissListener() {
            @Override
            public void onDismiss() {
                onShareSelectorDismiss();
            }
        }, mShareItemClick);
        mPlatformSelector.show();
    }

    public void showShareFullScreenWindow(View anchor) {
        mPlatformSelector = new PopFullScreenSharePlatformSelector(mContext, anchor, new BaseSharePlatformSelector.OnShareSelectorDismissListener() {
            @Override
            public void onDismiss() {
                onShareSelectorDismiss();
            }
        }, mShareItemClick);
        mPlatformSelector.show();
    }

    void onShareSelectorDismiss() {
        mCallback.onDismiss(this);
    }

    public void hideShareWindow() {
        if (mPlatformSelector != null)
            mPlatformSelector.dismiss();
    }

    private AdapterView.OnItemClickListener mShareItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ShareTarget item = (BaseSharePlatformSelector.ShareTarget) parent.getItemAtPosition(position);
            shareTo(item);
            hideShareWindow();
        }
    };

    public void shareTo(ShareTarget item) {
        BaseShareParam content = mCallback.getShareContent(ShareHelper.this, item.media);
        if (content == null) {
            return;
        }
        SocializeUtils.share(mContext, item.media, content, shareListener);
    }

    protected SocializeListeners.ShareListener shareListener = new SocializeListeners.ShareListenerAdapter() {

        @Override
        public void onStart(SocializeMedia type) {
            if (mCallback != null)
                mCallback.onShareStart(ShareHelper.this);
        }

        @Override
        protected void onComplete(SocializeMedia type, int code, Throwable error) {
            if (mCallback != null)
                mCallback.onShareComplete(ShareHelper.this, code);
        }
    };

    public Context getContext() {
        return mContext;
    }

    public void reset() {
        if (mPlatformSelector != null) {
            mPlatformSelector.release();
            mPlatformSelector = null;
        }
        mShareItemClick = null;
    }

    public interface Callback {
        BaseShareParam getShareContent(ShareHelper helper, SocializeMedia target);

        void onShareStart(ShareHelper helper);

        void onShareComplete(ShareHelper helper, int code);

        void onDismiss(ShareHelper helper);
    }

}
