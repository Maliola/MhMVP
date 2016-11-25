package com.jude.beam.expansion.list;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.jude.beam.R;
import com.jude.beam.Utils;
import com.jude.beam.expansion.NavigationBarActivity;
import com.jude.beam.expansion.NavigationBarFragment;
import com.jude.beam.loading.MewooHeaderLayout;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 */
public abstract class NavigationListFragment<T extends NavigationListFragmentPresenter, M> extends NavigationBarFragment<T> {

    private ListConfig mListConfig;
    private EasyRecyclerView mListView;
    private RecyclerArrayAdapter mAdapter;
    View mRootView;

    public EasyRecyclerView getListView() {
        return mListView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListConfig = getConfig();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initList();
        mListView.getSwipeToRefresh().setHeaderLayout(new MewooHeaderLayout(getContext()));
        mListView.setAdapterWithProgress(mAdapter = getPresenter().getAdapter());
        initAdapter();
    }

    @Override
    protected View onCreateView(Bundle savedInstanceState) {
        //createRecycler();
        View view = null;
        if (getLayout() != 0) {
            view = onCreateView(getLayout());
        } else if (mListConfig.mContainerLayoutRes != 0) {
            view = onCreateView(mListConfig.mContainerLayoutRes);
        } else if (mListConfig.mContainerLayoutView != null) {
            view = mListConfig.mContainerLayoutView;
        } else {
            EasyRecyclerView mListView = new EasyRecyclerView(getContext());
            mListView.setId(R.id.recycler);
            mListView.setLayoutManager(new LinearLayoutManager(getContext()));
            mListView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            view = mListView;
        }
        findRecycler(view);
        return view;
    }

    protected View onCreateView(int resId) {
        View view = View.inflate(getContext(), resId, null);
        return view;
    }

    public void stopRefresh() {
        mListView.getSwipeToRefresh().onRefreshComplete();
    }

    public void showError() {
        mListView.showError();
    }

    public int getLayout() {
        return 0;
    }

    private void findRecycler(View view) {
        mListView = (EasyRecyclerView) view.findViewById(R.id.recycler);
        if (mListView == null) throw new RuntimeException("No found recycler with id \"recycler\"");
        mListView.setLayoutManager(new LinearLayoutManager(getContext()));
    }


    private void initList() {
        if (mListConfig.mRefreshAble) mListView.setRefreshListener(getPresenter());
        else
            mListView.getSwipeToRefresh().setMode(PullToRefreshBase.Mode.DISABLED);
        if (mListConfig.mContainerProgressAble) {
            if (mListConfig.mContainerProgressView != null)
                mListView.setProgressView(mListConfig.mContainerProgressView);
            else mListView.setProgressView(mListConfig.mContainerProgressRes);
        }
        if (mListConfig.mContainerErrorAble) {
            if (mListConfig.mContainerErrorView != null)
                mListView.setErrorView(mListConfig.mContainerErrorView);
            else mListView.setErrorView(mListConfig.mContainerErrorRes);
        }
        if (mListConfig.mContainerEmptyAble) {
            if (mListConfig.mContainerEmptyView != null)
                mListView.setEmptyView(mListConfig.mContainerEmptyView);
            else mListView.setEmptyView(mListConfig.mContainerEmptyRes);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && mListConfig.mPaddingNavigationBarAble && Utils.hasSoftKeys(getContext())) {
            mListView.setRecyclerPadding(0, 0, 0, Utils.getNavigationBarHeight(getContext()));
        }
    }

    private void initAdapter() {
        if (mListConfig.mNoMoreAble) {
            if (mListConfig.mNoMoreView != null) mAdapter.setNoMore(mListConfig.mNoMoreView);
            else mAdapter.setNoMore(mListConfig.mNoMoreRes);
        }
        if (mListConfig.mLoadmoreAble) {
            if (mListConfig.mLoadMoreView != null)
                mAdapter.setMore(mListConfig.mLoadMoreView, getPresenter());
            else mAdapter.setMore(mListConfig.mLoadMoreRes, getPresenter());
        }
        if (mListConfig.mErrorAble) {
            View errorView;
            if (mListConfig.mErrorView != null)
                errorView = mAdapter.setMore(mListConfig.mErrorView, getPresenter());
            else errorView = mAdapter.setError(mListConfig.mErrorRes);
            if (mListConfig.mErrorTouchToResumeAble)
                errorView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mAdapter.resumeMore();
                    }
                });
        }
    }

    protected ListConfig getConfig() {
        return ListConfig.Default.clone();
    }

    public int getViewType(int position) {
        return 0;
    }

    public abstract BaseViewHolder getViewHolder(ViewGroup parent, int viewType);


}
