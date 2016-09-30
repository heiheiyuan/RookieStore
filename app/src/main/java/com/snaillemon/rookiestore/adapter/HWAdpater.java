package com.snaillemon.rookiestore.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.Button;

import com.facebook.drawee.view.SimpleDraweeView;
import com.snaillemon.rookiestore.R;
import com.snaillemon.rookiestore.bean.Wares;
import com.snaillemon.rookiestore.utils.ToastUtils;

import java.util.List;

/**
 * Created by GoodBoy on 9/29/2016.
 */

public class HWAdpater extends SimpleAdapter<Wares>{
    public HWAdpater(Context context,List<Wares> list) {
        super(context, R.layout.template_hot_cardview, list);
    }
    @Override
    protected void convert(BaseViewHolder holder, Wares item) {
        SimpleDraweeView draweeView = (SimpleDraweeView) holder.getView(R.id.drawee_view);
        draweeView.setImageURI(Uri.parse(item.getImgUrl()));
        holder.getTextView(R.id.hot_goods_title_tv).setText(item.getName());
        holder.getTextView(R.id.hot_goods_price_tv).setText("￥ " + item.getPrice());
        Button button = holder.getButtonView(R.id.hot_buy_btn);
        if (button != null) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtils.show(mContext,"Added to Cart");
                }
            });
        }
    }
    public void resetLayout(int layoutId) {
        this.layoutResId = layoutId;
        notifyItemRangeChanged(0,getDatas().size());
    }
}
