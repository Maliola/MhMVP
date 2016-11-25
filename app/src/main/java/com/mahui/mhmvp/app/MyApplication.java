package com.mahui.mhmvp.app;

import android.app.Application;
import com.mahui.mhmvp.util.JUtils;

/**
 * Created by Administrator on 2016/11/23.
 */

public class MyApplication extends Application{
    public static MyApplication instance;

    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = MyApplication.this;
        JUtils.initialize(MyApplication.this);
    }

}
