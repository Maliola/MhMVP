package com.mahui.mhmvp.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.list.BeamListFragment;
import com.jude.beam.expansion.list.ListConfig;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.mahui.mhmvp.R;
import com.mahui.mhmvp.presenter.ThreeFragmentPresenter;
import com.mahui.mhmvp.ui.viewholder.ThreeViewHolder;
import com.mahui.mhmvp.ui.viewholder.TwoViewHolder;

/**
 * Created by Administrator on 2016/11/7.
 */
@RequiresPresenter(ThreeFragmentPresenter.class)
public class ThreeFragment extends BeamListFragment<ThreeFragmentPresenter,String> {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public void setHeaderView(){
        getPresenter().getAdapter().addHeader(new ThreeFragment.HeadView());
    }
    public void setFootView(){
        getPresenter().getAdapter().addFooter(new ThreeFragment.FootView());
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected BaseViewHolder getViewHolder(ViewGroup parent, int viewType) {
        return new ThreeViewHolder(parent);
    }
    @Override
    protected ListConfig getConfig() {
        return super.getConfig().setRefreshAble(true).setContainerProgressRes(R.layout.self_loading)
                .setContainerEmptyRes(R.layout.self_empty);
    }
    public class HeadView implements RecyclerArrayAdapter.ItemView{
        SimpleDraweeView two;
        TextView user;
        @Override
        public View onCreateView(ViewGroup parent) {
            View headerView = getActivity().getLayoutInflater().inflate(R.layout.headview_layout, null);
            two= (SimpleDraweeView) headerView.findViewById(R.id.two);
            user= (TextView) headerView.findViewById(R.id.user);
            user.setText("是啊是啊");
            return headerView;
        }

        @Override
        public void onBindView(View headerView) {

        }
    }
    public class FootView implements RecyclerArrayAdapter.ItemView{
        SimpleDraweeView two;
        TextView user;
        @Override
        public View onCreateView(ViewGroup parent) {
            View headerView = getActivity().getLayoutInflater().inflate(R.layout.headview_layout, null);
            two= (SimpleDraweeView) headerView.findViewById(R.id.two);
            user= (TextView) headerView.findViewById(R.id.user);
            user.setText("是啊是啊");
            return headerView;
        }

        @Override
        public void onBindView(View headerView) {

        }
    }
}
