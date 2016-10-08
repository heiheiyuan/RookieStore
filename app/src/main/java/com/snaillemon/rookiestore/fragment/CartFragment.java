package com.snaillemon.rookiestore.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.snaillemon.rookiestore.R;
import com.snaillemon.rookiestore.adapter.CartAdapter;
import com.snaillemon.rookiestore.bean.ShoppingCart;
import com.snaillemon.rookiestore.utils.CartProvider;
import com.snaillemon.rookiestore.utils.Pager;
import com.snaillemon.rookiestore.widget.SlToolbar;

import java.util.List;

/**
 * Created by prince on 2016/9/19.
 */
public class CartFragment extends BaseFragment implements View.OnClickListener {
    private static final int ACTION_EDIT = 1;
    private static final int ACTION_COMPLETE = 2;
    @ViewInject(R.id.cart_recyclerview)
    private RecyclerView mRecyclerView;
    @ViewInject(R.id.cart_toolbar)
    private SlToolbar mToolbar;
    @ViewInject(R.id.cart_checkbox)
    private CheckBox mCheckBox;
    @ViewInject(R.id.cart_total_price_tv)
    private TextView mTotalPriceTv;
    @ViewInject(R.id.cart_calculate_btn)
    private Button mPayBtn;
    @ViewInject(R.id.cart_del_btn)
    private Button mDelBtn;
    private CartProvider mProvider;
    private CartAdapter mAdapter;

    @Override
    public void init() {
        mProvider = new CartProvider(getContext());
        changeToolbar();
        showData();
    }

    private void showData() {
        List<ShoppingCart> carts = mProvider.getAll();
        mAdapter = new CartAdapter(getContext(), carts, mCheckBox, mTotalPriceTv);
    }

    private void changeToolbar() {
        mToolbar.hideSearchView();
        mToolbar.showTitleView();
        mToolbar.setTitle(R.string.cart_title);
        mToolbar.getRightButton().setVisibility(View.VISIBLE);
        mToolbar.setRightButtonText(R.string.edit);
        mToolbar.getRightButton().setOnClickListener(this);
        mToolbar.getRightButton().setTag(ACTION_EDIT);
    }

    @Override
    public View createVew(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cart,container,false);
    }

    @Override
    public void onClick(View v) {
        int action = (int) v.getTag();
        if (action == ACTION_EDIT) {
            showDelControl();
        }else if (action == ACTION_COMPLETE) {
            hideDelControl();
        }
    }

    /**
     * complete edit
     */
    private void hideDelControl() {
        mToolbar.setRightButtonText(R.string.edit);
        mTotalPriceTv.setVisibility(View.VISIBLE);

        mDelBtn.setVisibility(View.GONE);
        mPayBtn.setVisibility(View.VISIBLE);
        mToolbar.setTag(ACTION_EDIT);
        mAdapter.checkAll_None(true);
        mAdapter.showTotalPrice();
        mCheckBox.setChecked(true);
    }

    /**
     * edit mode
     */
    private void showDelControl() {
        mToolbar.setRightButtonText(R.string.complete);
        mTotalPriceTv.setVisibility(View.GONE);

        mDelBtn.setVisibility(View.VISIBLE);
        mPayBtn.setVisibility(View.GONE);
        mToolbar.setTag(ACTION_COMPLETE);
        mAdapter.checkAll_None(false);
        mCheckBox.setChecked(false);
    }
}
