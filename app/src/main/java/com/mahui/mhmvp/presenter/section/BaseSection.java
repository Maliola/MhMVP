package com.mahui.mhmvp.presenter.section;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.mahui.mhmvp.model.BaseBean;

import java.util.List;

/**
 * Created by stone on 16/8/2.
 */
public  abstract class BaseSection<M, N extends BaseBean> extends StatelessSection<M,N> {
    M header;
    List<N> itemList;

    public BaseSection(M header, List<N> itemList , int headerId, int itemId) {
        super(headerId, itemId);
        this.header = header;
        this.itemList = itemList;
    }

    public BaseSection(M header, List<N> itemList , int headerId, int footerId, int itemId) {
        super(headerId, footerId,itemId);
        this.header = header;
        this.itemList = itemList;
    }

    @Override
    public void onBindItemViewHolder(BaseViewHolder<N> holder, int position) {
        if(position<itemList.size()){
            N n = itemList.get(position);
            n.setPosition(position);
            //n.init();
            holder.setData(n);
        }else{
            holder.setData(null);
        }

    }



    @Override
    public void onBindHeaderViewHolder(BaseViewHolder<M> holder) {
        holder.setData(header);
    }

    @Override
    public void onBindFooterViewHolder(BaseViewHolder<M> holder) {
        holder.setData(header);
    }


}
