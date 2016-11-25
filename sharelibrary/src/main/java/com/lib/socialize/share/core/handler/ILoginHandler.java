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

import com.lib.socialize.share.core.OnAuthListener;
import com.lib.socialize.share.core.SocializeMedia;

/**
 */
public interface ILoginHandler {

    void login(OnAuthListener onAuthListener) throws Exception;

    void release();

    Context getContext();

    SocializeMedia getShareMedia();

    void onActivityCreated(Activity activity, Bundle savedInstanceState, OnAuthListener listener);

    void onActivityNewIntent(Activity activity, Intent intent);

    void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data, OnAuthListener listener);

    void onActivityDestroy();

}
