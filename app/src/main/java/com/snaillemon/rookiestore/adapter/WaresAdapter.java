package com.snaillemon.rookiestore.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.snaillemon.rookiestore.R;
import com.snaillemon.rookiestore.bean.Wares;

import java.net.URI;
import java.util.List;

/**
 * Created by GoodBoy on 10/4/2016.
 */

public class WaresAdapter extends SimpleAdapter<Wares> {
    public WaresAdapter(Context context, List<Wares> datas) {
        super(context, R.layout.template_cate_right,datas);
    }

    @Override
    protected void convert(BaseViewHolder holder, Wares item) {
        SimpleDraweeView draweeView = (SimpleDraweeView) holder.getView(R.id.cate_drawee_view);
        draweeView.setImageURI(Uri.parse(item.getImgUrl()));
        holder.getTextView(R.id.cate_text_title).setText(item.getName());
        holder.getTextView(R.id.cate_text_price).setText("￥ " + item.getPrice());
    }
}
