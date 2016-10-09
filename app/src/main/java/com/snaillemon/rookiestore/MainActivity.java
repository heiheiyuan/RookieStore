package com.snaillemon.rookiestore;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import com.snaillemon.rookiestore.bean.Tab;
import com.snaillemon.rookiestore.fragment.CartFragment;
import com.snaillemon.rookiestore.fragment.CategoryFragment;
import com.snaillemon.rookiestore.fragment.HomeFragment;
import com.snaillemon.rookiestore.fragment.HotFragment;
import com.snaillemon.rookiestore.fragment.MineFragment;
import com.snaillemon.rookiestore.widget.FragmentTabHost;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements TabHost.OnTabChangeListener {
    private FragmentTabHost mTabHost;
    private LayoutInflater mInflater;
    private CartFragment mCartFragment;
    private ArrayList<Tab> mTabList = new ArrayList<>(5);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTab();
    }
    private void initTab() {
        //add tab to list
        Tab tab_home = new Tab(R.string.home, R.drawable.selector_icon_home, HomeFragment.class);
        Tab tab_hot = new Tab(R.string.hot, R.drawable.selector_icon_hot, HotFragment.class);
        Tab tab_category = new Tab(R.string.category, R.drawable.selector_icon_category, CategoryFragment.class);
        Tab tab_cart = new Tab(R.string.cart, R.drawable.selector_icon_cart, CartFragment.class);
        Tab tab_mime = new Tab(R.string.mime, R.drawable.selector_icon_mine, MineFragment.class);
        mTabList.add(tab_home);
        mTabList.add(tab_hot);
        mTabList.add(tab_category);
        mTabList.add(tab_cart);
        mTabList.add(tab_mime);
        //init layout inflater
        mInflater = LayoutInflater.from(this);
        //init view
        mTabHost = (FragmentTabHost) this.findViewById(android.R.id.tabhost);
        //important step
        mTabHost.setup(this,getSupportFragmentManager(),R.id.real_tab_content);
        //binding data
        for(Tab tab : mTabList) {
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(getString(tab.getTitle()));
            View view = buildIndicator(tab);
            tabSpec.setIndicator(view);
            mTabHost.addTab(tabSpec,tab.getFragment(),null);
        }
        //remove divider
        //mTabHost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);
        mTabHost.setOnTabChangedListener(this);
        mTabHost.setCurrentTab(0);
    }
    @NonNull
    private View buildIndicator(Tab tab) {
        //init view
        View view = mInflater.inflate(R.layout.tab_indicator, null);
        ImageView iconIv = (ImageView) view.findViewById(R.id.icon_tab);
        TextView textTv = (TextView) view.findViewById(R.id.text_tab);
        iconIv.setBackgroundResource(tab.getIcon());
        textTv.setText(tab.getTitle());
        return view;
    }

    @Override
    public void onTabChanged(String tabId) {
        if (tabId.equals(getString(R.string.cart))) refrshCartData();
    }

    private void refrshCartData() {
        if (mCartFragment == null) {
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(getString(R.string.cart));
            if (fragment != null) {
                mCartFragment = (CartFragment) fragment;
                mCartFragment.refreshData();
            }
        }else {
            mCartFragment.refreshData();
        }

    }
}
