package com.mahui.mhmvp.ui.viewholder;

import android.view.View;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.mahui.mhmvp.R;
import com.mahui.mhmvp.util.JUtils;

/**
 * Created by Administrator on 2016/11/28.
 */

public class HeadHolder extends BaseViewHolder<String> {
    TextView head;
    public HeadHolder(View itemView) {
        super(itemView);
        head= (TextView) itemView.findViewById(R.id.head);
        head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JUtils.Toast("点击头"+head.getText().toString());
            }
        });
    }

    @Override
    public void setData(String data) {
        super.setData(data);
        head.setText(data);
    }
}
