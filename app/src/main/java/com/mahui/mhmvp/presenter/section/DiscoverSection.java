package com.mahui.mhmvp.presenter.section;

import android.view.View;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.mahui.mhmvp.R;
import com.mahui.mhmvp.model.Person;
import com.mahui.mhmvp.presenter.adapter.SectionedRecyclerViewAdapter;
import com.mahui.mhmvp.ui.viewholder.FooterHolder;
import com.mahui.mhmvp.ui.viewholder.HeadHolder;
import com.mahui.mhmvp.ui.viewholder.TwoViewHolder;

import java.util.List;

/**
 * Created by stone on 16/8/2.
 */
public class DiscoverSection extends BaseSection<String, Person> {
    private SectionedRecyclerViewAdapter adapter;
    public DiscoverSection(String header, List<Person> itemList, SectionedRecyclerViewAdapter adapter) {
        super(header, itemList, R.layout.header_layout,R.layout.footer_layout, R.layout.twoviewholder_item);
        this.adapter = adapter;
    }

    @Override
    public BaseViewHolder getItemViewHolder(View view) {
        return new TwoViewHolder(view);
    }

    @Override
    public BaseViewHolder getHeaderViewHolder(View view) {
        return new HeadHolder(view);
    }

    @Override
    public BaseViewHolder getFooterViewHolder(View view) {
        return new FooterHolder(view,this);
    }

    @Override
    public int getContentItemsTotal() {
        return itemList.size();
    }

    public void resetList(List<Person> itemList){
        this.itemList.clear();
        this.itemList = itemList;
        //itemList.addAll(itemList);
        adapter.notifyDataSetChanged();
    }
}
