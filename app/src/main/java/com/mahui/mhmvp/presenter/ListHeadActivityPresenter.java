package com.mahui.mhmvp.presenter;

import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.jude.beam.expansion.list.NavigationListActivityPresenter;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.mahui.mhmvp.ui.activity.ListHeadActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/16.
 */

public class ListHeadActivityPresenter extends NavigationListActivityPresenter<ListHeadActivity,String> implements RecyclerArrayAdapter.OnItemClickListener{
    @Override
    protected void onCreateView(ListHeadActivity view) {
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
    @Override
    public void onLoadMore() {
        super.onLoadMore();
        getLoadMore();
    }
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
    public void getLoadMore(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<String> onelist=new ArrayList<String>();
                onelist.add("one---17");
                onelist.add("one---18");
                onelist.add("one---19");
                onelist.add("one---20");
                onelist.add("one---21");
                onelist.add("one---22");
                onelist.add("one---23");
                getAdapter().addAll(onelist);
                getAdapter().stopMore();
            }
        },1000);
    }

    @Override
    public void onItemClick(int position) {
        List<String> onelist=getAdapter().getAllData();
        Toast.makeText(getView(),onelist.get(position),Toast.LENGTH_SHORT).show();
    }
}
