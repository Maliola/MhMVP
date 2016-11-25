package com.jude.beam.expansion.list;

import android.view.View;

import com.jude.beam.R;

public class ListConfig implements Cloneable{
    public static ListConfig Default = new ListConfig();
    public static void setDefaultListConfig(ListConfig config){
        Default = config;
    }

    public boolean mRefreshAble = false;
    public boolean mLoadmoreAble = false;
    public boolean mNoMoreAble = true;
    public boolean mErrorAble = true;
    public boolean mErrorTouchToResumeAble = true;
    public boolean mContainerProgressAble = true;
    public boolean mContainerEmptyAble = true;
    public boolean mContainerErrorAble = true;
    public boolean mPaddingNavigationBarAble = false;
    boolean mStartWithProgress = true;

    public View mContainerLayoutView;
    public int mContainerLayoutRes = 0;
    public View mContainerEmptyView;
    public int mContainerEmptyRes = R.layout.beam_view_list_con_empty;
    public View mContainerProgressView;
    public int mContainerProgressRes = R.layout.beam_view_list_con_progress;
    public View mContainerErrorView;
    public int mContainerErrorRes = R.layout.beam_view_list_con_error;
    public View mLoadMoreView;
    public int mLoadMoreRes = R.layout.beam_view_list_more;
    public View mNoMoreView;
    public int mNoMoreRes = R.layout.beam_view_list_nomore;
    public View mErrorView;
    public int mErrorRes = R.layout.beam_view_list_error;


    public ListConfig setContainerLayoutView(View mContainerLayoutView) {
        this.mContainerLayoutView = mContainerLayoutView;
        return this;
    }

    public ListConfig setContainerLayoutRes(int mContainerLayoutRes) {
        this.mContainerLayoutRes = mContainerLayoutRes;
        return this;
    }
    public ListConfig setErrorAble(boolean mErrorAble) {
        this.mErrorAble = mErrorAble;
        return this;
    }

    public ListConfig setErrorView(View mErrorView) {
        this.mErrorView = mErrorView;
        return this;
    }

    public ListConfig setErrorRes(int mErrorRes) {
        this.mErrorRes = mErrorRes;
        return this;
    }

    public ListConfig setErrorTouchToResumeAble(boolean mErrorTouchToResumeAble) {
        this.mErrorTouchToResumeAble = mErrorTouchToResumeAble;
        if (mErrorTouchToResumeAble && mErrorRes == R.layout.beam_view_list_error)
            mErrorRes = R.layout.beam_view_list_error_retry;
        else if(!mErrorTouchToResumeAble && mErrorRes == R.layout.beam_view_list_error_retry)
            mErrorRes = R.layout.beam_view_list_error;
        return this;
    }

    public ListConfig setRefreshAble(boolean mRefreshAble) {
        this.mRefreshAble = mRefreshAble;
        return this;
    }

    public ListConfig setLoadmoreAble(boolean mLoadmoreAble) {
        this.mLoadmoreAble = mLoadmoreAble;
        return this;
    }

    public ListConfig setLoadMoreView(View mLoadMoreView) {
        this.mLoadMoreView = mLoadMoreView;
        return this;
    }

    public ListConfig setLoadMoreRes(int mLoadMoreRes) {
        this.mLoadMoreRes = mLoadMoreRes;
        return this;
    }

    public ListConfig setNoMoreView(View mNoMoreView) {
        this.mNoMoreView = mNoMoreView;
        return this;
    }

    public ListConfig setNoMoreAble(boolean mNoMoreAble) {
        this.mNoMoreAble = mNoMoreAble;
        return this;
    }
    public ListConfig setNoMoreRes(int mMoMoreRes) {
        this.mNoMoreRes = mMoMoreRes;
        return this;
    }

    public ListConfig setContainerEmptyView(View mContainerEmptyView) {
        this.mContainerEmptyView = mContainerEmptyView;
        return this;
    }

    public ListConfig setContainerEmptyRes(int mContainerEmptyRes) {
        this.mContainerEmptyRes = mContainerEmptyRes;
        return this;
    }

    public ListConfig setContainerProgressView(View mContainerProgressView) {
        this.mContainerProgressView = mContainerProgressView;
        return this;
    }

    public ListConfig setContainerProgressRes(int mContainerProgressRes) {
        this.mContainerProgressRes = mContainerProgressRes;
        return this;
    }

    public ListConfig setContainerErrorView(View mContainerErrorView) {
        this.mContainerErrorView = mContainerErrorView;
        return this;
    }
    public ListConfig setContainerErrorRes(int mContainerErrorRes) {
        this.mContainerErrorRes = mContainerErrorRes;
        return this;
    }
    public ListConfig setContainerProgressAble(boolean mContainerProgressAble) {
        this.mContainerProgressAble = mContainerProgressAble;
        return this;
    }

    public ListConfig setContainerEmptyAble(boolean mContainerEmptyAble) {
        this.mContainerEmptyAble = mContainerEmptyAble;
        return this;
    }

    public ListConfig setContainerErrorAble(boolean mContainerErrorAble) {
        this.mContainerErrorAble = mContainerErrorAble;
        return this;
    }

    public ListConfig setPaddingNavigationBarAble(boolean mPaddingNavigationBarAble){
        this.mPaddingNavigationBarAble = mPaddingNavigationBarAble;
        return this;
    }

    public ListConfig setStartWithProgress(boolean startWithProgress){
        this.mStartWithProgress = startWithProgress;
        return this;
    }

    @Override
    public ListConfig clone(){
        try {
            return (ListConfig) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return new ListConfig();
    }
}