package com.snaillemon.rookiestore.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lidroid.xutils.ViewUtils;

/**
 * Created by GoodBoy on 9/23/2016.
 */

public abstract class BaseFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = createVew(inflater,container,savedInstanceState);
        ViewUtils.inject(this,view);
        initToolbar();
        init();
        return view;
    }
    public abstract void init();
    public void initToolbar() {
    }
    public abstract View createVew(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);
}
