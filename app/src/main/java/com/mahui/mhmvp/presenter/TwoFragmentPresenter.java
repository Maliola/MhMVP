package com.mahui.mhmvp.presenter;

import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.jude.beam.expansion.list.BeamListFragmentPresenter;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.mahui.mhmvp.ui.fragment.TwoFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/7.
 */

public class TwoFragmentPresenter extends BeamListFragmentPresenter<TwoFragment,String> implements RecyclerArrayAdapter.OnItemClickListener{

    @Override
    protected void onCreateView(TwoFragment view) {
        super.onCreateView(view);
        getAdapter().setOnItemClickListener(this);
        onRefresh(null);
    }

    @Override
    public void onRefresh(PullToRefreshBase<RecyclerView> refreshView) {
        super.onRefresh(refreshView);
        getOneData();
        getAdapter().removeAllHeader();
        getView().setHeaderView();
    }

    /**
     * 获得OneFragment的数据
     */
    public void getOneData(){
        getAdapter().clear();
        List<String> onelist=new ArrayList<String>();
        onelist.add("one---1");
        onelist.add("one---2");
        onelist.add("one---3");
        onelist.add("one---4");
        onelist.add("one---5");
        onelist.add("one---6");
        onelist.add("one---7");
        onelist.add("one---8");
        onelist.add("one---9");
        onelist.add("one---10");
        onelist.add("one---11");
        onelist.add("one---12");
        onelist.add("one---13");
        onelist.add("one---14");
        onelist.add("one---15");
        onelist.add("one---16");
        getAdapter().removeAllHeader();
        getAdapter().addAll(onelist);
        getView().stopRefresh();
    }

    @Override
    public void onItemClick(int position) {
        List<String> onelist=getAdapter().getAllData();
        Toast.makeText(getView().getActivity(),onelist.get(position),Toast.LENGTH_SHORT).show();
    }
}
