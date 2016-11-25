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
public class NavigationListActivityPresenter<T extends NavigationListActivity,M> extends Presenter<T>
        implements RecyclerArrayAdapter.OnLoadMoreListener,PullToRefreshBase.OnRefreshListener<RecyclerView>{
    RecyclerArrayAdapter<M> mAdapter;
    int page = 0;
//    Subscriber<List<M>> mRefreshSubscriber = new Subscriber<List<M>>() {
//        @Override
//        public void onCompleted() {
//        }
//
//        @Override
//        public void onError(Throwable e) {
//            getView().showError();
//        }
//
//        @Override
//        public void onNext(List<M> ms) {
//            getAdapter().clear();
//            getAdapter().addAll(ms);
//            page = 1;
//        }
//
//        @Override
//        public void onStart() {
//        }
//    };

    @Override
    protected void onCreate(T view, Bundle savedState) {
        super.onCreate(view, savedState);
        Log.i("beam","F"+(getView()==null)+(view==null));

    }

//    Subscriber<List<M>> mMoreSubscriber = new Subscriber<List<M>>() {
//        @Override
//        public void onCompleted() {
//
//        }
//
//        @Override
//        public void onError(Throwable e) {
//            getAdapter().pauseMore();
//        }
//
//        @Override
//        public void onNext(List<M> ms) {
//            getAdapter().addAll(ms);
//            page++;
//        }
//    };

    public int getCurPage(){
        return page;
    }

    public void setCurPage(int page){
        this.page = page;
    }

//    public Subscriber<List<M>> getRefreshSubscriber(){
//        return mRefreshSubscriber;
//    }
//
//    public Subscriber<List<M>> getMoreSubscriber(){
//        return mMoreSubscriber;
//    }

    public RecyclerArrayAdapter<M> createDataAdapter(){
        Log.i("beam","F"+(getView()==null));
        return mAdapter = new DataAdapter(getView());
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
