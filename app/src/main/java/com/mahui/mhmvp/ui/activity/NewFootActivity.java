package com.mahui.mhmvp.ui.activity;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.list.ListConfig;
import com.jude.beam.expansion.list.NavigationListActivity;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.mahui.mhmvp.R;
import com.mahui.mhmvp.presenter.NewFootActivityPresenter;
import com.mahui.mhmvp.ui.viewholder.OneViewHolder;
import com.mahui.mhmvp.ui.viewholder.ThreeViewHolder;
import com.mahui.mhmvp.ui.viewholder.TwoViewHolder;

/**
 * Created by Administrator on 2016/11/16.
 */
@RequiresPresenter(NewFootActivityPresenter.class)
public class NewFootActivity extends NavigationListActivity<NewFootActivityPresenter,String> {
    @Override
    protected ListConfig getConfig() {
        return super.getConfig().setRefreshAble(true).setLoadmoreAble(false).setContainerProgressRes(R.layout.self_loading)
                .setContainerEmptyRes(R.layout.self_empty).setContainerLayoutRes(R.layout.onegragment_page);
    }

    @Override
    public BaseViewHolder getViewHolder(ViewGroup parent, int viewType) {
        return new ThreeViewHolder(parent);
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
        Toast.makeText(NewFootActivity.this,"编辑",Toast.LENGTH_SHORT).show();
    }
    public void setFootView(){
        getPresenter().getAdapter().addFooter(new FootView());
    }
    public class FootView implements RecyclerArrayAdapter.ItemView{

        SimpleDraweeView two;
        TextView user;
        @Override
        public View onCreateView(ViewGroup parent) {
            View headerView = getLayoutInflater().inflate(R.layout.headview_layout, null);
            //headerView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            two= (SimpleDraweeView) headerView.findViewById(R.id.two);
            user= (TextView) headerView.findViewById(R.id.user);
            user.setText("是啊是啊");
            //channel_detail_picture.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, height));
            return headerView;
        }

        @Override
        public void onBindView(View headerView) {

        }
    }
}
