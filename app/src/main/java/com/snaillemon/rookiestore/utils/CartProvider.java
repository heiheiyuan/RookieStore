package com.snaillemon.rookiestore.utils;

import android.content.Context;
import android.util.SparseArray;

import com.google.gson.reflect.TypeToken;
import com.snaillemon.rookiestore.bean.ShoppingCart;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by GoodBoy on 10/7/2016.
 */

public class CartProvider {
    private static final String CART_JSON = "cart_json";
    private SparseArray<ShoppingCart> datas = null;
    private Context mContext;

    public CartProvider(Context context) {
        mContext = context;
        datas = new SparseArray<>(10);
        listToSparse();
    }

    public void put(ShoppingCart cart) {
        ShoppingCart temp = datas.get(cart.getId().intValue());
        if (temp != null) {
            temp.setCount(temp.getCount() + 1);
        }else {
            temp = cart;
        }
        datas.put(cart.getId().intValue(),temp);
        commit();
    }
    public void update(ShoppingCart cart) {
        datas.put(cart.getId().intValue(),cart);
        commit();
    }
    public void delete(ShoppingCart cart) {
        datas.delete(cart.getId().intValue());
        commit();
    }
    public List<ShoppingCart> getAll() {

        return getDataFromLocal();
    }
    public void commit() {
        List<ShoppingCart> carts = sparseToList();
        PreferenceUtils.putString(mContext,CART_JSON,JSONUtil.toJSON(carts));
    }
    private List<ShoppingCart> sparseToList() {
        int size = datas.size();
        ArrayList<ShoppingCart> list = new ArrayList<>(size);
        for (int i = 0;i < size; i ++) {
            list.add(datas.valueAt(i));
        }
        return list;
    }
    public List<ShoppingCart> getDataFromLocal() {
        String json = PreferenceUtils.getString(mContext, CART_JSON);
        List<ShoppingCart> carts = null;
        if (json != null) {
            carts = JSONUtil.fromJson(json, new TypeToken<List<ShoppingCart>>() {}.getType());
        }
        return carts;
    }
    private void listToSparse() {
        List<ShoppingCart> carts = getDataFromLocal();
        if (carts != null && carts.size() > 0) {
            for (ShoppingCart cart : carts) {
                datas.put(cart.getId().intValue(),cart);
            }
        }
    }
}
