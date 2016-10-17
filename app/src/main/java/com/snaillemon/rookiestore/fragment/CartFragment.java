package com.snaillemon.rookiestore.fragment;

import android.content.Intent;
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
import com.snaillemon.rookiestore.WaresDetailsActivity;
import com.snaillemon.rookiestore.adapter.BaseAdapter;
import com.snaillemon.rookiestore.adapter.CartAdapter;
import com.snaillemon.rookiestore.adapter.decoration.DividerItemDecoration;
import com.snaillemon.rookiestore.bean.Contants;
import com.snaillemon.rookiestore.bean.ShoppingCart;
import com.snaillemon.rookiestore.bean.Wares;
import com.snaillemon.rookiestore.utils.CartProvider;
import com.snaillemon.rookiestore.widget.SlToolbar;

import java.util.List;

import static android.R.attr.action;
import static com.snaillemon.rookiestore.R.string.cart;

/**
 * Created by prince on 2016/9/19.
 */
public class CartFragment extends BaseFragment implements View.OnClickListener {
    //edit mode id
    private static final int ACTION_NORMAL = 1;
    //normal mode id
    private static final int ACTION_EDIT = 2;

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

    private List<ShoppingCart> mDelWare;
    private int mCurAction = ACTION_NORMAL;

    @Override
    public void init() {
        mProvider = CartProvider.getInstance(getContext());
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
        mToolbar.getRightButton().setTag(ACTION_NORMAL);
    }

    private void initEvents() {
        mDelBtn.setOnClickListener(this);
        mPayBtn.setOnClickListener(this);
    }

    private void showData() {
        List<ShoppingCart> carts = mProvider.getAll();
        mAdapter = new CartAdapter(getContext(), carts, mCheckBox, mTotalPriceTv);
        mAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ShoppingCart cart = mAdapter.getItem(position);
                if (mCurAction == ACTION_NORMAL) {
                    Intent intent = new Intent(getContext(), WaresDetailsActivity.class);
                    Wares ware = ShopCartToWare(cart);
                    intent.putExtra(Contants.WARE,ware);
                    startActivity(intent);
                }else if (mCurAction == ACTION_EDIT) {
                    cart.setChecked(!cart.isChecked());
                    mAdapter.notifyItemChanged(position);
                    mAdapter.updateCheckBox();
                }
            }
        });
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
                mDelWare = mAdapter.delWare();
                break;
            case R.id.cart_calculate_btn:
                // TODO: 10/9/2016 pay activity
                break;
            case R.id.toolbar_right_button:
                if (mCurAction == ACTION_NORMAL) {
                    showDelControl();
                    mCurAction = ACTION_EDIT;
                }else if (mCurAction == ACTION_EDIT) {
                    hideDelControl();
                    mCurAction = ACTION_NORMAL;
                    delLocalWares();
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
        mCurAction = ACTION_NORMAL;
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
        mCurAction = ACTION_EDIT;
        mAdapter.checkAll_None(false);
        mCheckBox.setChecked(false);
    }

    private void delLocalWares() {
        if (mDelWare != null && mDelWare.size() > 0) {
            for (int i = 0; i < mDelWare.size(); i++) {
                ShoppingCart cart = mDelWare.get(i);
                mProvider.delete(cart);
            }
        }
    }

    public void refreshData() {
        mAdapter.clear();
        initToolbar();
        hideDelControl();
        List<ShoppingCart> datas = mProvider.getAll();
        mAdapter.addDatas(datas);
        mAdapter.showTotalPrice();
    }
    private Wares ShopCartToWare(ShoppingCart cart) {
        Wares ware = new Wares();
        ware.setId(cart.getId());
        ware.setName(cart.getName());
        ware.setPrice(cart.getPrice());
        ware.setImgUrl(cart.getImgUrl());
        ware.setDescription(cart.getDescription());
        return ware;
    }
}
