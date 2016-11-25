package com.jude.beam.expansion.list;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.jude.beam.bijection.Presenter;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by Mr.Jude on 2015/8/17.
 */
public class NavigationListFragmentPresenter<T extends NavigationListFragment,M> extends Presenter<T>
        implements RecyclerArrayAdapter.OnLoadMoreListener,PullToRefreshBase.OnRefreshListener<RecyclerView>{
    RecyclerArrayAdapter<M> mAdapter;
    int page = 0;
    @Override
    protected void onCreate(T view, Bundle savedState) {
        super.onCreate(view, savedState);
        Log.i("beam","F"+(getView()==null)+(view==null));

    }
    public int getCurPage(){
        return page;
    }

    public void setCurPage(int page){
        this.page = page;
    }

    public RecyclerArrayAdapter<M> createDataAdapter(){
        Log.i("beam","F"+(getView()==null));
        return mAdapter = new DataAdapter(getView().getContext());
    }

    public RecyclerArrayAdapter<M> getAdapter(){
        if (mAdapter == null)mAdapter = createDataAdapter();
        return mAdapter;
    }

    @Override
    protected void onDestroyView() {
        super.onDestroyView();
        //mAdapterSubscription.unsubscribe();
        mAdapter = null;//Adapter与View的耦合根本解不开，所以也必须释放。
    }
    @Override
    public void onLoadMore() {
    }


    public void onRefresh() {
    }

    @Override
    public void onRefresh(PullToRefreshBase<RecyclerView> refreshView) {

    }

    public class DataAdapter extends RecyclerArrayAdapter<M> {

        public DataAdapter(Context context) {
            super(context);
        }

        @Override
        public int getViewType(int position) {
            return getView().getViewType(position);
        }

        @Override
        public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
            return getView().getViewHolder(parent, viewType);
        }
    }
}
