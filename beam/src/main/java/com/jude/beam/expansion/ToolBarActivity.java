package com.jude.beam.expansion;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.jude.beam.bijection.Presenter;

public class ToolBarActivity<T extends Presenter> extends BeamBaseActivity<T> {
    private FrameLayout mContent;
    private FrameLayout mContentParent;

    @Override
    public void preCreatePresenter() {
        super.preCreatePresenter();
        initViewTree();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void initViewTree() {
        mContentParent = (FrameLayout) findViewById(android.R.id.content);
        mContent = new FrameLayout(this);
        super.setContentView(mContent);
    }

    public FrameLayout getParentView() {
        return mContentParent;
    }


}
