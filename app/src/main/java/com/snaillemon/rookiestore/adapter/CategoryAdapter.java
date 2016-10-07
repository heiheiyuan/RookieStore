package com.snaillemon.rookiestore.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.snaillemon.rookiestore.R;
import com.snaillemon.rookiestore.bean.Category;
import com.snaillemon.rookiestore.utils.DensityUtil;

import java.util.List;

/**
 * Created by GoodBoy on 10/4/2016.
 */

public class CategoryAdapter extends SimpleAdapter<Category> {
    private int curSelectIndex = 0;

    public CategoryAdapter(Context context,List<Category> list) {
        super(context, R.layout.template_cate_left, list);
    }

    @Override
    protected void convert(BaseViewHolder holder, Category item) {
        TextView textView = holder.getTextView(R.id.cate_left_textview);
        textView.setText(item.getName());
        View view = holder.getView(R.id.left_item_ll);
        if (curSelectIndex == holder.getLayoutPosition()) {
            view.setBackgroundColor(mContext.getResources().getColor(R.color.bgwhite));
        }else {
            view.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        }
    }
    public void setSelectIndex(int selectIndex) {
        curSelectIndex = selectIndex;
    }
}
