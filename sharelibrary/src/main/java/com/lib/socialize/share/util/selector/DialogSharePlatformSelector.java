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

package com.lib.socialize.share.util.selector;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;

import com.lib.socialize.share.R;

/**
 */
public class DialogSharePlatformSelector extends BaseSharePlatformSelector {

    private final String mShareDialogTag;
    private Dialog mShareDialog;
    private AdapterView.OnItemClickListener mShareItemClick;

    private DialogInterface.OnDismissListener mDismiss;
    Activity context;

    public DialogSharePlatformSelector(Activity context, OnShareSelectorDismissListener dismissListener, AdapterView.OnItemClickListener itemClickListener) {
        super(context, dismissListener, itemClickListener);
        mShareDialogTag = "share.dialog" + context.getComponentName().getShortClassName();
        this.context = context;
    }

    @Override
    public void show() {

        if (mShareDialog == null)
            mShareDialog  = new Dialog(getMyContext(),R.style.sharedialogTheme);
        mShareDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (getDismissListener() != null)
                    getDismissListener().onDismiss();
            }
        });
        setOnItemClickListener(getItemClickListener());
        LayoutInflater inflater = (LayoutInflater) getMyContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // instantiate the dialog with the custom Theme
        GridView grid = createShareGridView(inflater.getContext(), mShareItemClick);
        mShareDialog.setContentView(grid);
        Window window = mShareDialog.getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        window.getDecorView().setSystemUiVisibility(getFlags());
        window.setGravity(Gravity.CENTER);  //此处可以设置dialog显示的位置
        WindowManager.LayoutParams lp = mShareDialog.getWindow().getAttributes();
        lp.gravity=Gravity.CENTER;
        DisplayMetrics dm = getMyContext().getResources().getDisplayMetrics();
        int w = dm.widthPixels;
        float width = (float)(w*0.7);
        lp.width =(int)width;
        lp.height=WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);

        mShareDialog.show();
    }

    private int getFlags(){

        int flags;
            int curApiVersion = android.os.Build.VERSION.SDK_INT;
            // This work only for android 4.4+
            if(curApiVersion >= Build.VERSION_CODES.KITKAT){
                // This work only for android 4.4+
                // hide navigation bar permanently in android activity
                // touch the screen, the navigation bar will not show
                flags = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_FULLSCREEN;

            }else{
                // touch the screen, the navigation bar will show
                flags = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            }
        return  flags;
    }

    @Override
    public void dismiss() {
        if (mShareDialog != null)
            mShareDialog.dismiss();
    }

    @Override
    public void release() {
        dismiss();
        mShareDialog = null;
        super.release();
    }


    public void setOnItemClickListener(AdapterView.OnItemClickListener click) {
        mShareItemClick = click;
    }

    public void setOnDismissListener(DialogInterface.OnDismissListener dismiss) {
        mDismiss = dismiss;
    }
}
