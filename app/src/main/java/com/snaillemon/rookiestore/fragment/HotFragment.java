package com.snaillemon.rookiestore.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cjj.MaterialRefreshLayout;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.snaillemon.rookiestore.R;
import com.snaillemon.rookiestore.WaresDetailsActivity;
import com.snaillemon.rookiestore.adapter.BaseAdapter;
import com.snaillemon.rookiestore.adapter.HWAdpater;
import com.snaillemon.rookiestore.bean.Contants;
import com.snaillemon.rookiestore.bean.Page;
import com.snaillemon.rookiestore.bean.Wares;
import com.snaillemon.rookiestore.utils.Pager;

import java.util.List;

import static android.R.attr.data;

/**
 * Created by prince on 2016/9/19.
 */
public class HotFragment extends BaseFragment implements Pager.OnPageListener<Wares>{
    @ViewInject(R.id.hot_recyclerview)
    private RecyclerView mRecyclerView;
    @ViewInject(R.id.hot_refresh)
    private MaterialRefreshLayout mRefreshView;
    private HWAdpater mAdpater;

    @Override
    public void init() {
        Pager pager = Pager.newBuilder()
                .setUrl(Contants.API.WARES_HOT)
                .setCanLoadMore(true)
                .setOnPageListener(this)
                .setPageSize(20)
                .setRefreshLayout(mRefreshView)
                .build(getContext(), new TypeToken<Page<Wares>>() {
                }.getType());
        pager.request();
    }
    @Override
    public View createVew(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hot,container,false);
    }

    @Override
    public void load(List<Wares> datas, int totalPage, int totalCount) {
        mAdpater = new HWAdpater(getContext(), datas);
        mAdpater.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Wares wares = mAdpater.getItem(position);
                Intent intent = new Intent(getActivity(), WaresDetailsActivity.class);
                intent.putExtra(Contants.WARE,wares);
                startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(mAdpater);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL_LIST));
    }

    @Override
    public void refresh(List<Wares> datas, int totalPage, int totalCount) {
        mAdpater.refreshDatas(datas);
        mRecyclerView.scrollToPosition(0);
    }

    @Override
    public void loadMore(List<Wares> datas, int totalPage, int totalCount) {
        mAdpater.loadMore(datas);
        mRecyclerView.scrollToPosition(mAdpater.getDatas().size());
    }
}
