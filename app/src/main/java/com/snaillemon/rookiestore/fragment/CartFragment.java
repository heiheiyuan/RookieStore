package com.snaillemon.rookiestore.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.snaillemon.rookiestore.R;

/**
 * Created by prince on 2016/9/19.
 */
public class CartFragment extends BaseFragment{
    @Override
    public void init() {

    }

    @Override
    public View createVew(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cart,container,false);
    }
}
