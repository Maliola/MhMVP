package com.mahui.mhmvp.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jude.beam.bijection.BeamFragment;
import com.jude.beam.bijection.RequiresPresenter;
import com.mahui.mhmvp.R;
import com.mahui.mhmvp.presenter.FourFragmentPresenter;
import com.mahui.mhmvp.ui.activity.ListHeadActivity;
import com.mahui.mhmvp.ui.activity.NewFootActivity;
import com.mahui.mhmvp.ui.activity.NewHeadActivity;
import com.mahui.mhmvp.ui.activity.NoallActivity;

/**
 * Created by Administrator on 2016/11/7.
 */
@RequiresPresenter(FourFragmentPresenter.class)
public class FourFragment extends BeamFragment<FourFragmentPresenter> implements View.OnClickListener{
    Button button1,button2,button3,button4;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.four_fragment, null);
        button1= (Button) view.findViewById(R.id.listhead);
        button2= (Button) view.findViewById(R.id.newhead);
        button3= (Button) view.findViewById(R.id.newfoot);
        button4= (Button) view.findViewById(R.id.noall);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        return view;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.listhead:
                Intent intent=new Intent(getActivity(), ListHeadActivity.class);
                startActivity(intent);
                break;
            case R.id.newhead:
                Intent intent1=new Intent(getActivity(), NewHeadActivity.class);
                startActivity(intent1);
                break;
            case R.id.newfoot:
                Intent intent2=new Intent(getActivity(), NewFootActivity.class);
                startActivity(intent2);
                break;
            case R.id.noall:
                Intent intent3=new Intent(getActivity(), NoallActivity.class);
                startActivity(intent3);
                break;
        }
    }
}
