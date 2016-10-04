package com.snaillemon.rookiestore.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.snaillemon.rookiestore.bean.Page;
import com.snaillemon.rookiestore.http.OkHttpHelper;
import com.snaillemon.rookiestore.http.SpotsCallback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Created by GoodBoy on 9/28/2016.
 */
public class Pager {
    private static Builder builder;
    private final OkHttpHelper mHttpHelper;
    private static final int STATE_NORMAL = 0;
    private static final int STATE_REFRESH = 1;
    private static final int STATE_MORE = 2;
    private int mCurrState = STATE_NORMAL;
    private Pager() {
        mHttpHelper = OkHttpHelper.getInstance();
        initRefreshLayout();
    }
    public static Builder newBuilder() {
        builder = new Builder();
        return builder;
    }
    public void request() {
        requestData();
    }
    public void putParams(String key,Object value) {
        builder.parmas.put(key,value);
    }
    private void initRefreshLayout() {
        builder.mRefreshLayout.setLoadMore(builder.mCanLoadMore);
        builder.mRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                builder.mRefreshLayout.setLoadMore(builder.mCanLoadMore);
                refresh();
            }
            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                if (builder.pageIndex <= builder.totalPage){
                    loadMore();
                } else{
                    Toast.makeText(builder.mContext,"no more data",Toast.LENGTH_LONG).show();
                    materialRefreshLayout.finishRefreshLoadMore();
                    builder.mRefreshLayout.setLoadMore(false);
                }
            }
        });
    }
    /**
     * request data
     */
    private void requestData() {
        String url = buildUrl();
        mHttpHelper.get(url,new RequestCallBack(builder.mContext));
    }
    /**
     * show data
     * @param datas
     * @param totalPage
     * @param totalCount
     * @param <T>
     */
    private <T> void showData(List<T> datas, int totalPage, int totalCount) {
        if (datas == null || datas.size() < 0){
            Toast.makeText(builder.mContext,"can not load data",Toast.LENGTH_LONG).show();
            return;
        }
        if (mCurrState == STATE_NORMAL) {
            if (builder.mListener != null) {
                builder.mListener.load(datas,totalPage,totalCount);
            }
        }
        if (mCurrState == STATE_REFRESH) {
            builder.mRefreshLayout.finishRefresh();
            if (builder.mListener != null) {
                builder.mListener.refresh(datas,totalPage,totalCount);
            }
        }
        if (mCurrState == STATE_MORE) {
            builder.mRefreshLayout.finishRefreshLoadMore();
            if (builder.mListener != null) {
                builder.mListener.loadMore(datas,totalPage,totalCount);
            }
        }
    }
    /**
     * refresh data
     */
    private void refresh() {
        mCurrState = STATE_REFRESH;
        builder.pageIndex = 1;
        requestData();
    }
    /**
     * load more data
     */
    private void loadMore() {
        mCurrState = STATE_MORE;
        ++builder.pageIndex;
        requestData();
    }
    /**
     * get request url
     * @return
     */
    private String buildUrl() {
        return builder.mUrl + "?" + buildUrlParams();
    }
    /**
     * split url
     * @return
     */
    private String buildUrlParams() {
        HashMap<String,Object> map = builder.parmas;
        map.put("curPage",builder.pageIndex);
        map.put("pageSize",builder.pageSize);
        Log.e("TAG","curPage = " + builder.pageIndex + "---" + "pageSize" + builder.pageSize);
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String,Object> entry : map.entrySet()) {
            sb.append(entry.getKey() + "=" + entry.getValue());
            sb.append("&");
        }
        String s = sb.toString();
        if (s.endsWith("&")) {
            s= s.substring(0,s.length()-1);
        }
        return s;
    }
    public static class Builder {
        private Context mContext;
        private Type mType;
        private String mUrl;
        private MaterialRefreshLayout mRefreshLayout;
        private boolean mCanLoadMore;
        private int totalPage = 1;
        private int pageIndex = 1;
        private int pageSize = 10;
        private OnPageListener mListener;
        private HashMap<String,Object> parmas = new HashMap<>(5);
        public Builder setUrl(String url) {
            builder.mUrl = url;
            return builder;
        }
        public Builder setPageSize(int pageSize) {
            this.pageSize = pageSize;
            return builder;
        }
        public Builder putParmas(String key,Object o) {
            parmas.put(key,o);
            return builder;
        }
        public Builder setRefreshLayout(MaterialRefreshLayout refreshLayout) {
            this.mRefreshLayout = refreshLayout;
            return builder;
        }
        public Builder setCanLoadMore(boolean canLoadMore) {
            this.mCanLoadMore = canLoadMore;
            return builder;
        }
        public Builder setOnPageListener(OnPageListener listener) {
            this.mListener = listener;
            return builder;
        }
        public Pager build(Context context,Type type) {
            this.mType = type;
            this.mContext = context;
            valid();
            return new Pager();
        }
        private void valid() {
            if (this.mContext == null) {
                throw new RuntimeException("context can not be null");
            }
            if (this.mUrl == null || "".equals(this.mUrl)) {
                throw new RuntimeException("url can not be null");
            }
            if (this.mRefreshLayout == null) {
                throw new RuntimeException("MaterialRefreshLayout can not be null");
            }
        }
    }
    class RequestCallBack<T> extends SpotsCallback<Page<T>>{
        public RequestCallBack(Context context) {
            super(context);
            super.mType = builder.mType;
        }
        @Override
        public void onSuccess(Response response, Page<T> page) {
            builder.pageIndex = page.getCurrentPage();
            builder.totalPage = page.getTotalPage();
            builder.pageSize = page.getPageSize();
            showData(page.getList(),page.getTotalPage(),page.getTotalCount());
        }
        @Override
        public void onError(Response response, int code, Exception e) {
            Toast.makeText(builder.mContext,"加载数据失败",Toast.LENGTH_LONG).show();
            if (mCurrState == STATE_REFRESH) {
                builder.mRefreshLayout.finishRefresh();
            }else if (mCurrState == STATE_MORE) {
                builder.mRefreshLayout.finishRefreshLoadMore();
            }
        }
        @Override
        public void onFailure(Request request, IOException e) {
            dismissDialog();
            Toast.makeText(builder.mContext,"request error",Toast.LENGTH_LONG).show();
            if (mCurrState == STATE_REFRESH) {
                builder.mRefreshLayout.finishRefresh();
            }else if (mCurrState == STATE_MORE) {
                builder.mRefreshLayout.finishRefreshLoadMore();
            }
        }
    }

    public interface OnPageListener<T>{
        void load(List<T> datas,int totalPage,int totalCount);
        void refresh(List<T> datas,int totalPage,int totalCount);
        void loadMore(List<T> datas,int totalPage,int totalCount);
    }
}
