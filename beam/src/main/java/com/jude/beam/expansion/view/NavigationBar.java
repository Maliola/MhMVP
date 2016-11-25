package com.jude.beam.expansion.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.jude.beam.R;

/**
 * 标题栏
 * 
 * 标题栏分为三个区域：左中右。左右宽度自由，用于放置button。中间填满空白。 三个容器内的组件都可单独设置和获取。
 * 
 */
public class NavigationBar extends RelativeLayout{
    
    private ViewGroup mFlNaviLeft;
    private ViewGroup mFlNaviMid;
    private ViewGroup mFlNaviRight;
    private View navigationRootView;
    private View navigationLineView;
    private Context mContext;
    
    public NavigationBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }
    
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        navigationRootView = findViewById(R.id.navigation_bar);
        mFlNaviLeft = (ViewGroup) findViewById(R.id.fl_navi_left);
        mFlNaviMid = (ViewGroup) findViewById(R.id.fl_navi_mid);
        mFlNaviRight = (ViewGroup) findViewById(R.id.fl_navi_right);
        navigationLineView =  findViewById(R.id.navi_bottom_line_v);
    }
    
    /**
     * 设置中间视图
     * 
     * @param view
     */
    public void setMiddleView(View view) {
        mFlNaviMid.removeAllViews();
        if (view != null) {
            mFlNaviMid.addView(view);
        }
    }
    
    public View getMiddleView() {
        return (mFlNaviMid.getChildCount() > 0) ? mFlNaviMid.getChildAt(0) : null;
    }
    
    /**
     * 设置左侧视图
     * 
     * @param view
     */
    public void setLeftView(View view) {
        mFlNaviLeft.removeAllViews();
        if (view != null) {
            mFlNaviLeft.addView(view);
        }
    }
    
    public View getLeftView() {
        return (mFlNaviLeft.getChildCount() > 0) ? mFlNaviLeft.getChildAt(0) : null;
    }
    
    /**
     * 设置右侧视图
     * 
     * @param view
     */
    public void setRightView(View view) {
        mFlNaviRight.removeAllViews();
        if (view != null) {
            mFlNaviRight.addView(view);
        }
    }
    
    public View getRightView() {
        return (mFlNaviRight.getChildCount() > 0) ? mFlNaviRight.getChildAt(0) : null;
    }
    
//    public void setBackgroundColor(int color){
//        navigationRootView.setBackgroundColor(color);
//    }
    
    public static interface IProvideNavigationBar {
        NavigationBar getNavigationBar();
        
        void setNavigationBar(NavigationBar navigationBar);
    }

}
