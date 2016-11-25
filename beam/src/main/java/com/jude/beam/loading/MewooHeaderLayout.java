package com.jude.beam.loading;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.LoadingLayoutBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.jude.beam.R;
import com.nineoldandroids.animation.ObjectAnimator;


/**
 * create by stone
 */
public class MewooHeaderLayout extends LoadingLayoutBase{

    static final String LOG_TAG = "MewooHeaderLayout";

    private FrameLayout mInnerLayout;

    private final TextView mSubHeaderText;

    private CharSequence mPullLabel;
    private CharSequence mRefreshingLabel;
    private CharSequence mReleaseLabel;

    private ImageView mPersonImage;
    private AnimationDrawable animP;

    public MewooHeaderLayout(Context context) {
        this(context, PullToRefreshBase.Mode.PULL_FROM_START);
    }

    public MewooHeaderLayout(Context context, PullToRefreshBase.Mode mode) {
        super(context);

        LayoutInflater.from(context).inflate(R.layout.mewoo_header_loadinglayout, this);

        mInnerLayout = (FrameLayout) findViewById(R.id.fl_inner);
        mSubHeaderText = (TextView) mInnerLayout.findViewById(R.id.pull_to_refresh_sub_text);
        mPersonImage = (ImageView) mInnerLayout.findViewById(R.id.pull_to_refresh_people);

        LayoutParams lp = (LayoutParams) mInnerLayout.getLayoutParams();
        lp.gravity = mode == PullToRefreshBase.Mode.PULL_FROM_END ? Gravity.TOP : Gravity.BOTTOM;

        // Load in labels
        mPullLabel = "下拉可以刷新";
        mRefreshingLabel = "正在刷新...";
        mReleaseLabel = "放手立即刷新";

        reset();
    }

    @Override
    public final int getContentSize() {
        return mInnerLayout.getHeight();
    }

    @Override
    public final void pullToRefresh() {
        mSubHeaderText.setText(mPullLabel);
    }

    @Override
    public final void onPull(float scaleOfLayout) {
        scaleOfLayout = scaleOfLayout > 1.0f ? 1.0f : scaleOfLayout;
        //透明度动画
        ObjectAnimator animAlphaP = ObjectAnimator.ofFloat(mPersonImage, "alpha", 0.5f, 1).setDuration(300);
        animAlphaP.setCurrentPlayTime((long) (scaleOfLayout * 300));
        //缩放动画
        //ViewHelper.setPivotX(mPersonImage, 0);  // 设置中心点
        //ViewHelper.setPivotY(mPersonImage, 0);
        //ViewHelper.setPivotX(mPersonImage, mPersonImage.getMeasuredHeight());
        //ViewHelper.setPivotY(mPersonImage, mPersonImage.getMeasuredWidth());
        ObjectAnimator animPX = ObjectAnimator.ofFloat(mPersonImage, "scaleX", 0.5f, 1).setDuration(300);
        animPX.setCurrentPlayTime((long) (scaleOfLayout * 300));
        ObjectAnimator animPY = ObjectAnimator.ofFloat(mPersonImage, "scaleY", 0.5f, 1).setDuration(300);
        animPY.setCurrentPlayTime((long) (scaleOfLayout * 300));

    }
    @Override
    public final void refreshing() {
        mSubHeaderText.setText(mRefreshingLabel);

        if (animP == null) {
            mPersonImage.setImageResource(R.drawable.mewoo_refreshing_anim);
            animP = (AnimationDrawable) mPersonImage.getDrawable();
        }
        animP.start();
    }

    @Override
    public final void releaseToRefresh() {
        mSubHeaderText.setText(mReleaseLabel);
    }

    @Override
    public final void reset() {
        if (animP != null) {
            animP.stop();
            animP = null;
        }
        mPersonImage.setImageResource(R.drawable.loading_1);
    }

    @Override
    public void setLastUpdatedLabel(CharSequence label) {
        mSubHeaderText.setText(label);
    }

    @Override
    public void setPullLabel(CharSequence pullLabel) {
        mPullLabel = pullLabel;
    }

    @Override
    public void setRefreshingLabel(CharSequence refreshingLabel) {
        mRefreshingLabel = refreshingLabel;
    }

    @Override
    public void setReleaseLabel(CharSequence releaseLabel) {
        mReleaseLabel = releaseLabel;
    }

    @Override
    public void setTextTypeface(Typeface tf) {
        mSubHeaderText.setTypeface(tf);
    }
}