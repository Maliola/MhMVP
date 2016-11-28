package com.mahui.mhmvp.ui.viewholder;

import android.view.View;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.mahui.mhmvp.R;
import com.mahui.mhmvp.presenter.section.DiscoverSection;
import com.mahui.mhmvp.util.JUtils;

/**
 * Created by Administrator on 2016/11/28.
 */

public class FooterHolder extends BaseViewHolder<String> {
    DiscoverSection discoverSection;
    TextView btn_refresh;
    public FooterHolder(View parent, DiscoverSection discoverSection) {
        super(parent);
        this.discoverSection = discoverSection;
        btn_refresh= (TextView) parent.findViewById(R.id.btn_refresh);
        btn_refresh.setOnClickListener(foot);
    }
    View.OnClickListener foot=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            JUtils.Toast("点击脚");
        }
    };
}
