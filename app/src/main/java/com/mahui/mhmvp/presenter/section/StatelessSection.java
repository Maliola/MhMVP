package com.mahui.mhmvp.presenter.section;

import android.view.View;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Abstract Section with no States.
 *
 *
 */
public abstract class StatelessSection<M,N> extends Section<M,N> {

    /**
     * Create a Section object with loading/failed states but no header and footer
     * @param itemResourceId layout resource for its items
     */
    public StatelessSection(int itemResourceId) {
        super();
        this.itemResourceId = itemResourceId;
    }

    /**
     * Create a Section object with loading/failed states, a custom header but no footer
     * @param headerResourceId layout resource for its header
     * @param itemResourceId layout resource for its items
     */
    public StatelessSection(int headerResourceId, int itemResourceId) {
        this(itemResourceId);
        this.headerResourceId = headerResourceId;
        this.hasHeader = true;
    }

    /**
     * Create a Section object with loading/failed states, a custom header and footer
     * @param headerResourceId layout resource for its header
     * @param footerResourceId layout resource for its footer
     * @param itemResourceId layout resource for its items
     */
    public StatelessSection(int headerResourceId, int footerResourceId, int itemResourceId) {
        this(headerResourceId, itemResourceId);
        this.footerResourceId = footerResourceId;
        this.hasFooter = true;
    }

    @Override
    public final void onBindLoadingViewHolder(BaseViewHolder holder) {
        super.onBindLoadingViewHolder(holder);
    }

    @Override
    public final BaseViewHolder getLoadingViewHolder(View view) {
        return super.getLoadingViewHolder(view);
    }

    @Override
    public final void onBindFailedViewHolder(BaseViewHolder holder) {
        super.onBindFailedViewHolder(holder);
    }

    @Override
    public final BaseViewHolder getFailedViewHolder(View view) {
        return super.getFailedViewHolder(view);
    }
}
