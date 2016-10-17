package com.snaillemon.rookiestore.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.snaillemon.rookiestore.R;
import com.snaillemon.rookiestore.WaresDetailsActivity;
import com.snaillemon.rookiestore.bean.Contants;
import com.snaillemon.rookiestore.bean.ShoppingCart;
import com.snaillemon.rookiestore.bean.Wares;
import com.snaillemon.rookiestore.utils.CartProvider;
import com.snaillemon.rookiestore.widget.NumberAddSubView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.snaillemon.rookiestore.R.string.cart;

/**
 * Created by GoodBoy on 10/8/2016.
 */
public class CartAdapter extends SimpleAdapter<ShoppingCart>{

    private TextView mTotalPriceTv;

    private CheckBox mCheckBox;

    private final CartProvider mProvider;

    public CartAdapter(Context context, List<ShoppingCart> carts, CheckBox checkBox, TextView totalPriceTv) {
        super(context, R.layout.template_cart,carts);
        setCheckBox(checkBox);
        setTextView(totalPriceTv);
        mProvider = CartProvider.getInstance(context);
    }

    private void setTextView(TextView totalPriceTv) {
        mTotalPriceTv = totalPriceTv;
    }

    private void setCheckBox(CheckBox checkBox) {
        mCheckBox = checkBox;
        mCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAll_None(mCheckBox.isChecked());
                showTotalPrice();
            }
        });
    }

    public void checkAll_None(boolean checked) {
        if (!notNull()) {
            return;
        }
        int i = 0;
        for (ShoppingCart cart:datas) {
            cart.setChecked(checked);
            notifyItemChanged(i);
            i ++;
        }
    }

    @Override
    protected void convert(BaseViewHolder holder, final ShoppingCart cart) {
        final CheckBox cb = (CheckBox) holder.getView(R.id.item_ware_checkbox);

        cb.setChecked(cart.isChecked());

        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cart.setChecked(!cart.isChecked());
                cb.setChecked(cart.isChecked());
                updatePrice(cart.getCount(),cart);
            }
        });

        SimpleDraweeView sdv = (SimpleDraweeView) holder.getView(R.id.item_ware_pic_sdv);

        sdv.setImageURI(Uri.parse(cart.getImgUrl()));

        holder.getTextView(R.id.item_ware_title_tv).setText(cart.getName());

        holder.getTextView(R.id.item_ware_price_tv).setText("$" + cart.getPrice());

        NumberAddSubView nasv = (NumberAddSubView) holder.getView(R.id.item_ware_addsub_btn);

        nasv.setValue(cart.getCount());

        nasv.setOnButtonClickListener(new NumberAddSubView.OnButtonClickListener() {
            @Override
            public void onBttonAddClick(View view, int value) {
                updatePrice(value, cart);
            }

            @Override
            public void onBttonSubClick(View view, int value) {
                updatePrice(value, cart);
            }
        });
    }

    private void updatePrice(int value, ShoppingCart cart) {
        cart.setCount(value);
        mProvider.update(cart);
        showTotalPrice();
        notifyDataSetChanged();
    }

    public void showTotalPrice() {
        float total = getTotalPrice();
        mTotalPriceTv.setText(Html.fromHtml("合计 ￥<span style = 'color:#ffff5454'>" + total),TextView.BufferType.SPANNABLE);
    }

    private float getTotalPrice() {
        float sum = 0;
        if (!notNull()) {
            return sum;
        }
        for (ShoppingCart cart:datas) {
            if (cart.isChecked()) {
                sum += cart.getPrice() * cart.getCount();
            }
        }
        return sum;
    }

    private boolean notNull() {
        return (datas != null && datas.size() > 0);
    }

    public List<ShoppingCart> delWare() {
        List<ShoppingCart> delList = new ArrayList<>();
        if (!notNull()) return null;
        for (Iterator<ShoppingCart> iterator = datas.iterator();iterator.hasNext();) {
            ShoppingCart cart = iterator.next();
            if (cart.isChecked()) {
                int i = datas.indexOf(cart);
                delList.add(cart);
                iterator.remove();
                notifyItemRemoved(i);
            }
        }
        return delList;
    }

    /**
     * update state of check box
     */
    public void updateCheckBox() {
        if (datas != null) {
            int count = datas.size();
            int checkNum = 0;
            for (int i = 0; i < count; i++) {
                if (!datas.get(i).isChecked()) {
                    mCheckBox.setChecked(false);
                }else {
                    checkNum ++;
                }
            }
            if (checkNum == count) {
                mCheckBox.setChecked(true);
            }
        }
    }
}
