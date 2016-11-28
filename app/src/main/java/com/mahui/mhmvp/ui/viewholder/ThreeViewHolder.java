package com.mahui.mhmvp.ui.viewholder;

import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.mahui.mhmvp.R;

/**
 * Created by Administrator on 2016/11/7.
 */

public class ThreeViewHolder extends BaseViewHolder<String> {
    TextView title;
    public ThreeViewHolder(ViewGroup parent) {
        super(parent, R.layout.threeviewholder_item);
        title= (TextView) itemView.findViewById(R.id.title);
    }

    @Override
    public void setData(String data) {
        title.setText(data);
    }
}
