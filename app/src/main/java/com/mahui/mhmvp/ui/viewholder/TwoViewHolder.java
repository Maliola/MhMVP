package com.mahui.mhmvp.ui.viewholder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.mahui.mhmvp.R;
import com.mahui.mhmvp.model.Person;
import com.mahui.mhmvp.util.JUtils;

/**
 * Created by Administrator on 2016/11/7.
 */

public class TwoViewHolder extends BaseViewHolder<Person> {
    TextView name,sex;

    public TwoViewHolder(View itemView) {
        super(itemView);
        name= (TextView) itemView.findViewById(R.id.name);
        sex= (TextView) itemView.findViewById(R.id.sex);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JUtils.Toast("点击body"+name.getText().toString());
            }
        });
    }

    @Override
    public void setData(Person data) {
        name.setText(data.getName());
        sex.setText(data.getSex());
    }
}
