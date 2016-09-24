package com.snaillemon.rookiestore.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.snaillemon.rookiestore.bean.Campaign;
import com.snaillemon.rookiestore.bean.HomeCampaign;

import java.util.List;

/**
 * Created by GoodBoy on 9/24/2016.
 */
public class HomeCategotyAdapter extends RecyclerView.Adapter<HomeCategotyAdapter.ViewHoler>{
    private List<HomeCampaign> mData;
    private Context mContext;
    private LayoutInflater mInflater;
    private static int VIEW_TYPE_L = 0;
    private static int VIEW_TYPE_R = 1;
    private OnCampaignClickListener mListener;

    public HomeCategotyAdapter(List<HomeCampaign> data, Context context) {
        mData = data;
        this.mContext = context;
    }
    private void setOnCampaignClickListener(OnCampaignClickListener listener) {
        mListener = listener;
    }
    @Override
    public ViewHoler onCreateViewHolder(ViewGroup parent, int viewType) {
        mInflater = LayoutInflater.from(mContext);
        if (viewType == VIEW_TYPE_R) {
            return new ViewHoler(mInflater.inflate())
        }
        return null;
    }
    @Override
    public void onBindViewHolder(ViewHoler holder, int position) {
    }
    @Override
    public int getItemCount() {
        return 0;
    }
    class ViewHoler extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ViewHoler(View itemView) {
            super(itemView);
        }
        @Override
        public void onClick(View v) {
        }
    }
    public interface OnCampaignClickListener {
        void onClick(View view, Campaign campaign);
    }
}
