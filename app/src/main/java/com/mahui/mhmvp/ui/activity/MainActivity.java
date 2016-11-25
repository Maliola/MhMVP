package com.mahui.mhmvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.mahui.mhmvp.R;
import com.mahui.mhmvp.app.MyApplication;
import com.mahui.mhmvp.app.Page;
import com.mahui.mhmvp.presenter.MainActivityPresenter;
import com.mahui.mhmvp.ui.fragment.FourFragment;
import com.mahui.mhmvp.ui.fragment.OneFragment;
import com.mahui.mhmvp.ui.fragment.ThreeFragment;
import com.mahui.mhmvp.ui.fragment.TwoFragment;

import java.util.HashMap;

@RequiresPresenter(MainActivityPresenter.class)
public class MainActivity extends BeamBaseActivity<MainActivityPresenter> implements View.OnClickListener {
    private int DEF_TAB_COUNT = 4;
    // 底部Tab栏
    private TabHost mTabHost;
    private TabManager mTabManager;
    private LinearLayout mBottomTabs;
    private Button[] mTabButtons;
    private int mCurrentIdx = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(MainActivity.this);
        setContentView(R.layout.activity_main);
        mTabHost = (TabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup();
        initContent();
        initTabButtons();
        mTabButtons[0].setSelected(true);
    }
    @Override
    public void onClick(View v) {
        for (int i = 0; i < mTabButtons.length; i++) {
            mTabButtons[i].setSelected(false);

            if (v == mTabButtons[i]) {
                v.setSelected(true);
                // 点击当前页标签刷新。
                // if (mCurrentIdx == i) {
                // Fragment fragment = mTabManager.getLastTabFragment();
                // if (fragment instanceof IClickToRefersh) {
                // ((IClickToRefersh) fragment).onClickRefersh();
                // }
                // } else {
                mCurrentIdx = i;
                mTabHost.setCurrentTab(mCurrentIdx);
                /*Fragment fragment = mTabManager.getLastTabFragment();
                if (fragment instanceof IClickToRefersh) {
                    ((IClickToRefersh) fragment).onClickRefersh();
                }*/
                // }
            }
        }
    }

    protected void initTabButtons() {
        mBottomTabs = (LinearLayout) findViewById(R.id.main_radio);
        if (null == mBottomTabs) {
            throw new IllegalArgumentException("Your TabHost must have a RadioGroup whose id attribute is 'main_radio'");
        }

        mTabButtons = new Button[DEF_TAB_COUNT];
        for (int j = 0; j < DEF_TAB_COUNT; j++) {
            String str = "radio_button_" + j;
            mTabButtons[j] = (Button) mBottomTabs.findViewWithTag(str);
            mTabButtons[j].setOnClickListener(this);
        }

        mTabButtons[0].performClick();
    }

    private int tab = 4;
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if(intent!=null){
            Page page =(Page)intent.getSerializableExtra("page");
            if(page!=null){
                String index = page.getArgs().get("tab");
                if (!TextUtils.isEmpty(index)) {
                    tab = Integer.parseInt(page.getArgs().get("tab"));
                }
            }else{
                tab=3;
            }
            mTabHost.setCurrentTab(tab);
            mTabButtons[mCurrentIdx].setSelected(false);
            mCurrentIdx = tab;
            mTabButtons[tab].setSelected(true);
        }

    }


    public Button[] getmTabButtons() {
        return mTabButtons;
    }

    protected void initContent() {
        mTabManager = new TabManager(this, mTabHost, R.id.real_tab_content);
        mTabManager.addTab(mTabHost.newTabSpec("1").setIndicator("One"), OneFragment.class, null);
        mTabManager.addTab(mTabHost.newTabSpec("2").setIndicator("Two"), TwoFragment.class, null);
        mTabManager.addTab(mTabHost.newTabSpec("3").setIndicator("Three"), ThreeFragment.class, null);
        mTabManager.addTab(mTabHost.newTabSpec("4").setIndicator("Four"), FourFragment.class, null);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("tab", mTabHost.getCurrentTabTag());
    }

    @Override
    public void onBackPressed() {
        exitApp();
    }


    public long exitTime = 0;

    private void exitApp() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(MainActivity.this,"再点击一次退出应用",Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }

    /**
     * This is a helper class that implements a generic mechanism for
     * associating fragments with the tabs in a tab host. It relies on a trick.
     * Normally a tab host has a simple API for supplying a View or Intent that
     * each tab will show. This is not sufficient for switching between
     * fragments. So instead we make the content part of the tab host 0dp high
     * (it is not shown) and the TabManager supplies its own dummy view to show
     * as the tab content. It listens to changes in tabs, and takes care of
     * switch to the correct fragment shown in a separate content area whenever
     * the selected tab changes.
     */
    public static class TabManager implements TabHost.OnTabChangeListener {
        private final FragmentActivity mActivity;
        private final TabHost mTabHost;
        private final int mContainerId;
        private final HashMap<String, TabInfo> mTabs = new HashMap<String, TabInfo>();
        TabInfo mLastTab;

        static final class TabInfo {
            private final String tag;
            private final Class<?> clss;
            private final Bundle args;
            private Fragment fragment;

            TabInfo(String _tag, Class<?> _class, Bundle _args) {
                tag = _tag;
                clss = _class;
                args = _args;
            }
        }

        static class DummyTabFactory implements TabHost.TabContentFactory {
            private final Context mContext;

            public DummyTabFactory(Context context) {
                mContext = context;
            }

            @Override
            public View createTabContent(String tag) {
                View v = new View(mContext);
                v.setMinimumWidth(0);
                v.setMinimumHeight(0);
                return v;
            }
        }

        public TabManager(FragmentActivity activity, TabHost tabHost, int containerId) {
            mActivity = activity;
            mTabHost = tabHost;
            mContainerId = containerId;
            mTabHost.setOnTabChangedListener(this);
        }

        public void addTab(TabHost.TabSpec tabSpec, Class<?> clss, Bundle args) {
            tabSpec.setContent(new DummyTabFactory(mActivity));
            String tag = tabSpec.getTag();

            TabInfo info = new TabInfo(tag, clss, args);

            // Check to see if we already have a fragment for this tab, probably
            // from a previously saved state. If so, deactivate it, because our
            // initial state is that a tab isn't shown.
            info.fragment = mActivity.getSupportFragmentManager().findFragmentByTag(tag);
            if (info.fragment != null && !info.fragment.isDetached()) {
                FragmentTransaction ft = mActivity.getSupportFragmentManager().beginTransaction();
                ft.detach(info.fragment);
                ft.commitAllowingStateLoss();
            }

            mTabs.put(tag, info);
            mTabHost.addTab(tabSpec);
        }

        public Fragment getLastTabFragment() {
            return mLastTab.fragment;
        }
        @Override
        public void onTabChanged(String tabId) {

            TabInfo newTab = mTabs.get(tabId);
            if (mLastTab != newTab) {
                FragmentTransaction ft = mActivity.getSupportFragmentManager().beginTransaction();
                if (mLastTab != null) {
                    if (mLastTab.fragment != null) {
                        ft.hide(mLastTab.fragment);
                        // ft.detach(mLastTab.fragment);
                    }
                }
                if (newTab != null) {
                    if (newTab.fragment == null) {
                        newTab.fragment = Fragment.instantiate(mActivity, newTab.clss.getName(), newTab.args);
                        ft.add(mContainerId, newTab.fragment, newTab.tag);
                    } else {
                        if (newTab.fragment.isHidden())
                            ft.show(newTab.fragment);
                        else
                            ft.attach(newTab.fragment);
                    }
                }
                mLastTab = newTab;
                ft.commitAllowingStateLoss();
                mActivity.getSupportFragmentManager().executePendingTransactions();
            }
        }
    }

}
