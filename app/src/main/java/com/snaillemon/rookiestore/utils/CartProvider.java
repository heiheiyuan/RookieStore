package com.snaillemon.rookiestore.utils;

import android.content.Context;
import android.util.SparseArray;

import com.google.gson.reflect.TypeToken;
import com.snaillemon.rookiestore.bean.ShoppingCart;
import com.snaillemon.rookiestore.bean.Wares;

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
            temp.setCount(1);
        }
        //add to memory
        datas.put(cart.getId().intValue(),temp);
        //save to local
        commit();
    }
    public void put(Wares ware) {
        //transfer ware to ShoppingCart
        ShoppingCart cart = convertData(ware);
        put(cart);
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
    private void listToSparse() {
        List<ShoppingCart> carts = getDataFromLocal();
        if (carts != null && carts.size() > 0) {
            for (ShoppingCart cart : carts) {
                datas.put(cart.getId().intValue(),cart);
            }
        }
    }
    public List<ShoppingCart> getDataFromLocal() {
        String json = PreferenceUtils.getString(mContext, CART_JSON);
        List<ShoppingCart> carts = null;
        if (json != null) {
            carts = JSONUtil.fromJson(json, new TypeToken<List<ShoppingCart>>() {}.getType());
        }
        return carts;
    }
    private ShoppingCart convertData(Wares ware) {
        ShoppingCart cart = new ShoppingCart();
        cart.setId(ware.getId());
        cart.setDescription(ware.getDescription());
        cart.setImgUrl(ware.getImgUrl());
        cart.setName(ware.getName());
        cart.setPrice(ware.getPrice());
        return cart;
    }
}
