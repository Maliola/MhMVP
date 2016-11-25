package com.mahui.mhmvp.ui.fragment;

import android.view.ViewGroup;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.list.BeamListFragment;
import com.jude.beam.expansion.list.ListConfig;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.mahui.mhmvp.R;
import com.mahui.mhmvp.presenter.OneFragmentPresenter;
import com.mahui.mhmvp.ui.viewholder.OneViewHolder;

/**
 * Created by Administrator on 2016/11/7.
 */
@RequiresPresenter(OneFragmentPresenter.class)
public class OneFragment extends BeamListFragment<OneFragmentPresenter,String> {
    @Override
    protected BaseViewHolder getViewHolder(ViewGroup parent, int viewType) {
        return new OneViewHolder(parent);
    }

    @Override
    protected ListConfig getConfig() {
        return super.getConfig().setRefreshAble(true).setLoadmoreAble(true).setContainerProgressRes(R.layout.self_loading)
                .setContainerEmptyRes(R.layout.self_empty).setContainerLayoutRes(R.layout.onegragment_page);
    }

}
