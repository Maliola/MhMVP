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

import com.lib.socialize.share.core.LibShareConfiguration;
import com.lib.socialize.share.core.SharePlatformConfig;
import com.lib.socialize.share.core.SocializeListeners;
import com.lib.socialize.share.core.SocializeMedia;
import com.lib.socialize.share.core.error.ShareStatusCode;
import com.lib.socialize.share.core.handler.AbsShareHandler;
import com.lib.socialize.share.core.shareparam.BaseShareParam;
import com.lib.socialize.share.core.ui.SinaAssistActivity;

import java.util.Map;

/**
 */
public class SinaShareTransitHandler extends AbsShareHandler {

    public static final int REQ_CODE = 10233;

    public SinaShareTransitHandler(Activity context, LibShareConfiguration configuration) {
        super(context, configuration);
    }

    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data, SocializeListeners.ShareListener listener) {
        super.onActivityResult(activity, requestCode, resultCode, data, listener);
        if (data == null || getShareListener() == null) {
            return;
        }

        int statusCode = data.getIntExtra(SinaAssistActivity.KEY_CODE, -1);
        if (statusCode == ShareStatusCode.ST_CODE_SUCCESSED) {
            getShareListener().onSuccess(SocializeMedia.SINA, ShareStatusCode.ST_CODE_SUCCESSED);
        } else if (statusCode == ShareStatusCode.ST_CODE_ERROR) {
            getShareListener().onError(SocializeMedia.SINA, ShareStatusCode.ST_CODE_SHARE_ERROR_SHARE_FAILED, new Exception());
        } else if (statusCode == ShareStatusCode.ST_CODE_ERROR_CANCEL) {
            getShareListener().onCancel(SocializeMedia.SINA);
        }

    }

    @Override
    public void share(final BaseShareParam params, final SocializeListeners.ShareListener listener) throws Exception {
        super.share(params, listener);
        mImageHelper.saveBitmapToExternalIfNeed(params);
        mImageHelper.copyImageToCacheFileDirIfNeed(params);
        mImageHelper.downloadImageIfNeed(params, new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getContext(), SinaAssistActivity.class);
                intent.putExtra(SinaAssistActivity.KEY_PARAM, params);
                Map<String, Object> appConfig = SharePlatformConfig.getPlatformDevInfo(SocializeMedia.SINA);
                if (appConfig != null) {
                    intent.putExtra(SinaAssistActivity.KEY_APPKEY, (String) appConfig.get(SharePlatformConfig.APP_KEY));
                }
                intent.putExtra(SinaAssistActivity.KEY_CONFIG, mShareConfiguration);
                ((Activity) getContext()).startActivityForResult(intent, REQ_CODE);
            }
        });
    }

    @Override
    public SocializeMedia getShareMedia() {
        return SocializeMedia.SINA;
    }

    @Override
    protected boolean isNeedActivityContext() {
        return true;
    }

}
