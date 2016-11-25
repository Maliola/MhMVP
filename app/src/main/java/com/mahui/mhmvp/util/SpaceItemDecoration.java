package com.mahui.mhmvp.util;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2016/11/23.
 */

public class SpaceItemDecoration extends RecyclerView.ItemDecoration{

    private int space;
    private int count=3;
    public SpaceItemDecoration(int space) {
        this.space = space;
    }
    public SpaceItemDecoration(int space,int count) {
        this.space = space;
        this.count= count;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //由于每行都只有3个，所以第一个都是3的倍数，把左边距设为0
        if(count==2){
            if(parent.getChildLayoutPosition(view)>-1){
                outRect.left = space;
                outRect.bottom = space;
                if ((parent.getChildLayoutPosition(view)-1) %count==0) {
                    outRect.left = space/2;
                    outRect.right = space;
                }else{
                    outRect.left = space;
                    outRect.right = space/2;
                }
            }
        }else{
            outRect.left = space;
            outRect.bottom = space;
            if (parent.getChildLayoutPosition(view) %count==0) {
                outRect.left = 0;
            }
        }
    }
}
