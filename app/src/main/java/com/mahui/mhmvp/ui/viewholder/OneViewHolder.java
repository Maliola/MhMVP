package com.mahui.mhmvp.ui.viewholder;

import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.mahui.mhmvp.R;
import com.mahui.mhmvp.util.JUtils;

/**
 * Created by Administrator on 2016/11/7.
 */

public class OneViewHolder extends BaseViewHolder<String> {
    TextView title;
    SimpleDraweeView pic;
    public OneViewHolder(ViewGroup parent) {
        super(parent, R.layout.oneviewholder_item);
        title = (TextView) itemView.findViewById(R.id.title);
        pic= (SimpleDraweeView) itemView.findViewById(R.id.pic);
        int width= JUtils.getScreenWidth()/2;
        int height=(int)(((float)8 / 15) * width);
        pic.setLayoutParams(new LinearLayout.LayoutParams(width,height));
    }

    @Override
    public void setData(String data) {
        title.setText(data);
    }
}
