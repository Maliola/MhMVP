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

import com.lib.socialize.share.core.LibShareConfiguration;
import com.lib.socialize.share.core.SocializeMedia;
import com.lib.socialize.share.core.handler.qq.QQLoginHandler;
import com.lib.socialize.share.core.handler.sina.SinaLoginHandler;
import com.lib.socialize.share.core.handler.wx.WxLoginHandler;

import java.util.HashMap;
import java.util.Map;

/**
 */
public class LoginHandlerPool {

    private static LoginHandlerPool ourInstance = new LoginHandlerPool();
    private static Map<SocializeMedia, ILoginHandler> mHandlerMap = new HashMap<>();
    public static void release(){
        mHandlerMap.clear();
    }
    private LoginHandlerPool() {
    }

    public static ILoginHandler newHandler(Activity context, SocializeMedia type, LibShareConfiguration shareConfiguration) {
        ILoginHandler handler = null;
        switch (type) {
            case WEIXIN:
                handler = new WxLoginHandler(context, shareConfiguration);
                break;
            case SINA:
                handler = new SinaLoginHandler(context, shareConfiguration);
                break;
            case QQ:
                handler = new QQLoginHandler(context, shareConfiguration);
                break;
        }

        ourInstance.mHandlerMap.put(type, handler);

        return handler;
    }

    public static ILoginHandler getCurrentHandler(SocializeMedia type) {
        return ourInstance.mHandlerMap.get(type);
    }

    public static void remove(SocializeMedia type) {
        ourInstance.mHandlerMap.remove(type);
    }

}
