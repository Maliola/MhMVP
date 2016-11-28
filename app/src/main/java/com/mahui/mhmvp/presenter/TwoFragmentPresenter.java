package com.mahui.mhmvp.presenter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.jude.beam.expansion.list.BeamListFragmentPresenter;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.mahui.mhmvp.model.Person;
import com.mahui.mhmvp.presenter.adapter.SectionedRecyclerViewAdapter;
import com.mahui.mhmvp.presenter.section.DiscoverSection;
import com.mahui.mhmvp.ui.fragment.TwoFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/7.
 */

public class TwoFragmentPresenter extends BeamListFragmentPresenter<TwoFragment,Person>{
    List<Person> persons;
    List<String> onelist;
    @Override
    protected void onCreateView(TwoFragment view) {
        super.onCreateView(view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getView().getActivity(), 2);
        gridLayoutManager.setSpanSizeLookup(((SectionedRecyclerViewAdapter) getAdapter()).obtainSectionGridSpanSizeLookUp(2));
        getView().getListView().setLayoutManager(gridLayoutManager);
        onRefresh(null);
    }

    @Override
    public void onRefresh(PullToRefreshBase<RecyclerView> refreshView) {
        super.onRefresh(refreshView);
        getPerson();
        getAdapter().removeAllHeader();
        getView().setHeaderView();
    }

    public void getPerson(){
        onelist=new ArrayList<String>();
        onelist.add("111");
        onelist.add("222");
        onelist.add("333");
        onelist.add("444");
        onelist.add("555");
        onelist.add("666");
        persons=new ArrayList<Person>();
        persons.add(new Person("111","男"));
        persons.add(new Person("222","男"));
        persons.add(new Person("333","男"));
        persons.add(new Person("444","男"));
        persons.add(new Person("555","男"));
        persons.add(new Person("666","男"));
        persons.add(new Person("777","男"));
        persons.add(new Person("888","男"));
        persons.add(new Person("999","男"));
        persons.add(new Person("000","男"));
        ((SectionedRecyclerViewAdapter) getAdapter()).removeAllSections();
        for(String group:onelist){
            ((SectionedRecyclerViewAdapter) getAdapter()).addSection(new DiscoverSection(group,persons,((SectionedRecyclerViewAdapter) getAdapter())));
        }
        getAdapter().notifyDataSetChanged();
        getView().stopRefresh();
    }
    @Override
    public RecyclerArrayAdapter<Person> createDataAdapter() {
        return new SectionedRecyclerViewAdapter<>(getView().getContext());
    }
}
