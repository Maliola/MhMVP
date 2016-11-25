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
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.lib.socialize.share.R;
import com.lib.socialize.share.core.SocializeMedia;

/**
 */
public abstract class BaseSharePlatformSelector {

    private Activity mContext;
    private OnShareSelectorDismissListener mDismissListener;
    private AdapterView.OnItemClickListener mItemClickListener;
    //private static Handler sHandler = new Handler();

    private static ShareTarget[] shareTargets = {
            new ShareTarget(SocializeMedia.SINA, R.string.bili_socialize_text_sina_key, R.drawable.bili_socialize_sina_on),
            new ShareTarget(SocializeMedia.WEIXIN, R.string.bili_socialize_text_weixin_key, R.drawable.bili_socialize_wechat),
            new ShareTarget(SocializeMedia.WEIXIN_MONMENT, R.string.bili_socialize_text_weixin_circle_key, R.drawable.bili_socialize_wxcircle),
            new ShareTarget(SocializeMedia.QQ, R.string.bili_socialize_text_qq_key, R.drawable.bili_socialize_qq_on),
            new ShareTarget(SocializeMedia.QZONE, R.string.bili_socialize_text_qq_zone_key, R.drawable.bili_socialize_qzone_on),
            new ShareTarget(SocializeMedia.GENERIC, R.string.bili_share_sdk_others, R.drawable.bili_socialize_sms_on),
            //new ShareTarget(SocializeMedia.COPY, R.string.bili_socialize_text_copy_url, R.drawable.bili_socialize_copy_url)
    };

    public BaseSharePlatformSelector(Activity context, OnShareSelectorDismissListener dismissListener, AdapterView.OnItemClickListener itemClickListener) {
        mContext = context;
        mDismissListener = dismissListener;
        mItemClickListener = itemClickListener;
//                sHandler.post(mHideRunnable); // hide the navigation bar
//        final View decorView = context.getWindow().getDecorView();
//        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener()
//        {
//            @Override
//            public void onSystemUiVisibilityChange(int visibility)
//            {
//                sHandler.post(mHideRunnable); // hide the navigation bar
//            }
//        });
    }

    public abstract void show();

    public abstract void dismiss();

    public void release() {
        mContext = null;
        mDismissListener = null;
        mItemClickListener = null;
    }

    protected static GridView createShareGridView(final Context context, AdapterView.OnItemClickListener onItemClickListener) {
        GridView grid = new GridView(context);
        ListAdapter adapter = new ArrayAdapter<ShareTarget>(context, 0, shareTargets) {
            // no need scroll
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bili_socialize_shareboard_item, parent, false);
                view.setBackgroundDrawable(null);
                ImageView image = (ImageView) view.findViewById(R.id.bili_socialize_shareboard_image);
                TextView platform = (TextView) view.findViewById(R.id.bili_socialize_shareboard_pltform_name);

                ShareTarget target = getItem(position);
                image.setImageResource(target.iconId);
                platform.setText(target.titleId);
                return view;
            }
        };
        grid.setNumColumns(-1);
        grid.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
        grid.setGravity(Gravity.CENTER);
        grid.setColumnWidth(context.getResources().getDimensionPixelSize(R.dimen.bili_socialize_shareboard_size));
        grid.setLayoutParams(new ViewGroup.LayoutParams(-1, -2));
        grid.setSelector(R.drawable.bili_socialize_selector_item_background);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(onItemClickListener);
        return grid;
    }

    public Activity getMyContext() {
        return mContext;
    }

    public AdapterView.OnItemClickListener getItemClickListener() {
        return mItemClickListener;
    }

    public OnShareSelectorDismissListener getDismissListener() {
        return mDismissListener;
    }

    public static class ShareTarget {
        public int titleId;
        public int iconId;
        public SocializeMedia media;

        public ShareTarget(SocializeMedia media, int titleId, int iconId) {
            this.media = media;
            this.iconId = iconId;
            this.titleId = titleId;
        }
    }

    public interface OnShareSelectorDismissListener {
        void onDismiss();
    }

//    Runnable mHideRunnable = new Runnable() {
//        @Override
//        public void run() {
//            int flags;
//            int curApiVersion = android.os.Build.VERSION.SDK_INT;
//            // This work only for android 4.4+
//            if(curApiVersion >= Build.VERSION_CODES.KITKAT){
//                // This work only for android 4.4+
//                // hide navigation bar permanently in android activity
//                // touch the screen, the navigation bar will not show
//                flags = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                        | View.SYSTEM_UI_FLAG_IMMERSIVE
//                        | View.SYSTEM_UI_FLAG_FULLSCREEN;
//
//            }else{
//                // touch the screen, the navigation bar will show
//                flags = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
//            }
//
//            // must be executed in main thread :)
//            mContext.getWindow().getDecorView().setSystemUiVisibility(flags);
//        }
//    };

}
