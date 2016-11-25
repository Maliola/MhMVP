package com.mahui.mhmvp.ui.activity;

import android.view.ViewGroup;
import android.widget.Toast;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.list.ListConfig;
import com.jude.beam.expansion.list.NavigationListActivity;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.mahui.mhmvp.R;
import com.mahui.mhmvp.presenter.NewHeadActivityPresenter;
import com.mahui.mhmvp.ui.viewholder.OneViewHolder;
import com.mahui.mhmvp.ui.viewholder.TwoViewHolder;

/**
 * Created by Administrator on 2016/11/16.
 */
@RequiresPresenter(NewHeadActivityPresenter.class)
public class NewHeadActivity extends NavigationListActivity<NewHeadActivityPresenter,String>{
    @Override
    protected ListConfig getConfig() {
        return super.getConfig().setRefreshAble(true).setLoadmoreAble(false).setContainerProgressRes(R.layout.self_loading)
                .setContainerEmptyRes(R.layout.self_empty).setContainerLayoutRes(R.layout.onegragment_page);
    }

    @Override
    public BaseViewHolder getViewHolder(ViewGroup parent, int viewType) {
        return new TwoViewHolder(parent);
    }

    @Override
    public void initData() {
        super.initData();
        setTitle("主标题");
        setRightTitle("编辑");
        setLeftDrawable(R.mipmap.toolbar_back_black);
    }

    @Override
    protected void onLeftCLick() {
        super.onLeftCLick();
        onBackPressed();
        finish();
    }

    @Override
    protected void onRightClick() {
        Toast.makeText(NewHeadActivity.this,"编辑",Toast.LENGTH_SHORT).show();
    }

}
