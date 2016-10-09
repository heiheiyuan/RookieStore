package com.snaillemon.rookiestore.fragment;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
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
import com.snaillemon.rookiestore.adapter.decoration.DividerItemDecoration;
import com.snaillemon.rookiestore.bean.ShoppingCart;
import com.snaillemon.rookiestore.utils.CartProvider;
import com.snaillemon.rookiestore.widget.SlToolbar;

import java.util.List;

/**
 * Created by prince on 2016/9/19.
 */
public class CartFragment extends BaseFragment implements View.OnClickListener {
    //edit mode id
    private static final int ACTION_EDIT = 1;
    //normal mode id
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
        initEvents();
        showData();
    }

    @Override
    public void initToolbar() {
        mToolbar.hideSearchView();
        mToolbar.showTitleView();
        mToolbar.setTitle(R.string.cart_title);
        mToolbar.getRightButton().setVisibility(View.VISIBLE);
        mToolbar.setRightButtonText(R.string.edit);
        mToolbar.getRightButton().setOnClickListener(this);
        mToolbar.getRightButton().setTag(ACTION_EDIT);
    }

    private void initEvents() {
        mDelBtn.setOnClickListener(this);
        mPayBtn.setOnClickListener(this);
    }

    private void showData() {
        List<ShoppingCart> carts = mProvider.getAll();
        mAdapter = new CartAdapter(getContext(), carts, mCheckBox, mTotalPriceTv);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL_LIST));
    }

    @Override
    public View createVew(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cart,container,false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cart_del_btn:
                mAdapter.delWare();
                break;
            case R.id.cart_calculate_btn:
                // TODO: 10/9/2016 pay activity
                break;
            case R.id.toolbar_right_button:
                int action = (int) v.getTag();
                if (action == ACTION_EDIT) {
                    showDelControl();
                }else if (action == ACTION_COMPLETE) {
                    hideDelControl();
                }
                break;
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
        mToolbar.getRightButton().setTag(ACTION_EDIT);
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
        mToolbar.getRightButton().setTag(ACTION_COMPLETE);
        mAdapter.checkAll_None(false);
        mCheckBox.setChecked(false);
    }

    public void refreshData() {
        mAdapter.clear();
        List<ShoppingCart> datas = mProvider.getAll();
        mAdapter.addDatas(datas);
        mAdapter.showTotalPrice();
    }
}
