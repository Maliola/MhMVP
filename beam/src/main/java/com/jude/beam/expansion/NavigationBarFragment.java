package com.jude.beam.expansion;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jude.beam.R;
import com.jude.beam.bijection.BeamFragment;
import com.jude.beam.bijection.Presenter;
import com.jude.beam.expansion.view.NavigationBar;


public class NavigationBarFragment<T extends Presenter> extends BeamFragment<T> implements NavigationBar.IProvideNavigationBar, OnClickListener {

    private static final String TAG = "NavigationBarActivity";
    private Dialog mLoadDialog;
    private NavigationBar mNavigationBar;
    private TextView leftView;
    private TextView rightView;
    private TextView titleView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.activity_base_title_layout,container,false);
        setupNavigationBar(view);
        View content = onCreateView(savedInstanceState);
        if (content != null) {
            ViewGroup group = (ViewGroup) view.findViewById(R.id.fragment_content);
            if (group != null)
                group.addView(content, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        }

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
    }

    protected void setupNavigationBar(View view) {
        mNavigationBar = (NavigationBar)view.findViewById(R.id.navigation_bar);
        if (mNavigationBar == null) {
            throw new RuntimeException("R.id.navigation_bar_ex resouce not found!!");
        }
        View middleview = onAddMiddleView();
        if (middleview != null)
            mNavigationBar.setMiddleView(middleview); // 自定义中间title

        View leftView = onAddLeftView();
        if (leftView != null)
            mNavigationBar.setLeftView(leftView);// 自定义左边title

        View rightView = onAddRightView();
        if (rightView != null)
            mNavigationBar.setRightView(rightView);// 自定义右边title
    }

    protected View onAddMiddleView() {
        titleView = (TextView) View.inflate(getContext(),R.layout.navigation_bar_title, null);
        titleView.setOnClickListener(this);
        return titleView;
    }

    protected View onAddLeftView() {
        leftView = (TextView) View.inflate(getContext(),R.layout.navigation_bar_left, null);
        leftView.setOnClickListener(this);
        return leftView;
    }

    protected View onAddRightView() {
        rightView = (TextView)View.inflate(getContext(),R.layout.navigation_bar_right, null);
        rightView.setOnClickListener(this);
        return rightView;
    }

    @Override
    public NavigationBar getNavigationBar() {
        if (mNavigationBar == null) {
            throw new RuntimeException("you may have forgotten to call setupNavigationBar!!");
        }
        return mNavigationBar;
    }

    public void setTitle(int titleId) {
        titleView.setText(titleId);
    }

    public void setTitle(String title) {
        titleView.setText(title);
    }

    public void setTitle(Spannable title) {
        titleView.setText(title);
    }

    public void setLeftDrawable(int resid) {
        if (leftView != null) {
            leftView.setBackgroundResource(resid);
        }
    }

    public void setRightDrawable(int resid) {
        if (rightView != null) {
            rightView.setBackgroundResource(resid);
        }
    }

    public void setRightTitle(int resid) {
        if (rightView != null) {
            rightView.setText(resid);
        }
    }

    public void setRightTitle(String title) {
        if (rightView != null) {
            rightView.setText(title);
        }
    }

    public void setLeftTitle(int resid) {
        if (leftView != null) {
            leftView.setText(resid);
        }
    }

    public void setLeftTitle(String title) {
        if (leftView != null) {
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(50,50,0,0);//4个参数按顺序分别是左上右下

            leftView.setLayoutParams(layoutParams);
            leftView.setText(title);
        }
    }

    public void initData() {

    }

    public void initView() {

    }


    protected View onCreateView(Bundle savedInstanceState) {
        return null;
    }


    /**
     * 隐藏左边
     *
     * @param ishide
     */
    public void hideLeftView(boolean ishide) {
        if (ishide && leftView != null) {
            leftView.setVisibility(View.GONE);
        } else if (leftView != null) {
            leftView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 隐藏右边
     *
     * @param ishide
     */
    public void hideRightView(boolean ishide) {
        if (ishide && rightView != null) {
            rightView.setVisibility(View.GONE);
        } else if (rightView != null) {
            rightView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setNavigationBar(NavigationBar navigationBar) {

    }

    protected void onLeftCLick() {

    }

    protected void onRightClick() {

    }

    @Override
    public void onClick(View v) {
        if (v == leftView) {
            onLeftCLick();
        } else if (v == rightView) {
            onRightClick();
        }
    }

}
