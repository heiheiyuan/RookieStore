package com.snaillemon.rookiestore.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by GoodBoy on 9/29/2016.
 */

public abstract class SimpleAdapter<T> extends BaseAdapter<T,BaseViewHolder> {
    public SimpleAdapter(Context context, int layoutResId) {
        super(context, layoutResId);
    }
    public SimpleAdapter(Context context, int layoutResId, List<T> list) {
        super(context, layoutResId,list);
    }
}
