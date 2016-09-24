package com.snaillemon.rookiestore.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.snaillemon.rookiestore.R;
import com.snaillemon.rookiestore.bean.Campaign;
import com.snaillemon.rookiestore.bean.HomeCampaign;
import com.squareup.picasso.Picasso;

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
            return new ViewHoler(mInflater.inflate(R.layout.template_home_cardview2,parent,false));
        }
        return new ViewHoler(mInflater.inflate(R.layout.template_home_cardview,parent,false));
    }
    @Override
    public void onBindViewHolder(ViewHoler holder, int position) {
        HomeCampaign homeCampaign = mData.get(position);
        holder.textTitle.setText(homeCampaign.getTitle());
        Picasso.with(mContext).load(homeCampaign.getCpOne().getImgUrl()).into(holder.imageViewRight);
        Picasso.with(mContext).load(homeCampaign.getCpTwo().getImgUrl()).into(holder.imageViewSmallTop);
        Picasso.with(mContext).load(homeCampaign.getCpThree().getImgUrl()).into(holder.imageViewSmallBottom);
    }
    @Override
    public int getItemCount() {
        return mData.size();
    }
    class ViewHoler extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textTitle;
        ImageView imageViewRight;
        ImageView imageViewSmallTop;
        ImageView imageViewSmallBottom;
        public ViewHoler(View itemView) {
            super(itemView);
            textTitle = (TextView) itemView.findViewById(R.id.home_title_tv);
            imageViewRight = (ImageView) itemView.findViewById(R.id.home_right_iv);
            imageViewSmallTop = (ImageView) itemView.findViewById(R.id.home_top_iv);
            imageViewSmallBottom = (ImageView) itemView.findViewById(R.id.home_bottom_iv);
            imageViewRight.setOnClickListener(this);
            imageViewSmallTop.setOnClickListener(this);
            imageViewSmallBottom.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            if(mListener != null) {
                animator(v);
            }
        }
    }
    private void animator(View v) {

    }
    public interface OnCampaignClickListener {
        void onClick(View view, Campaign campaign);
    }
}
