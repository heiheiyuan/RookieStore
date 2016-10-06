package com.snaillemon.rookiestore.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by GoodBoy on 9/29/2016.
 */

public abstract class BaseAdapter<T,H extends BaseViewHolder> extends RecyclerView.Adapter<BaseViewHolder> {
    protected static final String TAG = BaseAdapter.class.getSimpleName();
    protected final Context mContext;
    protected int layoutResId;
    protected List<T> datas;
    private OnItemClickListener mOnItemClickListener = null;


    public interface OnItemClickListener {
        void onItemClick(View view,int position);
    }
    public BaseAdapter(Context context, int layoutResId) {
        this(context,layoutResId,null);
    }
    public BaseAdapter(Context context,int layoutResId,List<T> datas) {
        this.datas = datas == null ? new ArrayList<T>() : datas;
        this.mContext = context;
        this.layoutResId = layoutResId;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutResId,parent,false);
        BaseViewHolder holder = new BaseViewHolder(view, mOnItemClickListener);
        return holder;
    }
    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        T item = getItem(position);
        convert((H)holder,item);
    }
    protected abstract void convert(H holder, T item);

    public T getItem(int position) {
        if (position >= datas.size()) return null;
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        if (datas == null || datas.size() <= 0){
            return 0;
        }
        return datas.size();
    }
    public void clear() {
        for (Iterator it = datas.iterator();it.hasNext();) {
            T t = (T) it.next();
            int positon = datas.indexOf(t);
            it.remove();
            notifyItemRemoved(positon);
        }
    }
    /**
     * delete item
     */
    public void removeItem(T t) {
        int position = datas.indexOf(t);
        datas.remove(position);
        notifyItemRemoved(position);
    }
    public List<T> getDatas() {
        return datas;
    }
    public void addDatas(List<T> datas) {
        addDatas(0,datas);
    }
    public void addDatas(int position,List<T> list) {
        if (list != null || list.size() > 0) {
            for (T t : list) {
                datas.add(position,t);
                notifyItemInserted(position);
            }
        }
    }
    public void refreshDatas(List<T> list) {
        if (list != null && list.size() > 0) {
            clear();
            int size = list.size();
            for (int i = 0;i < size ; i++) {
                datas.add(i,list.get(i));
                notifyItemInserted(i);
            }
        }
    }
    public void loadMore(List<T> list) {
        if (list != null && list.size() > 0) {
            int size = list.size();
            int begin = datas.size();
            for (int i = 0;i < size ; i++) {
                datas.add(list.get(i));
                notifyItemInserted(i + begin);
            }
        }
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}
