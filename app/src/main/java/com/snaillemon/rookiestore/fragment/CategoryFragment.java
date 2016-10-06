package com.snaillemon.rookiestore.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.snaillemon.rookiestore.R;
import com.snaillemon.rookiestore.WaresDetailsActivity;
import com.snaillemon.rookiestore.adapter.BaseAdapter;
import com.snaillemon.rookiestore.adapter.CategoryAdapter;
import com.snaillemon.rookiestore.adapter.WaresAdapter;
import com.snaillemon.rookiestore.adapter.decoration.DividerItemDecoration;
import com.snaillemon.rookiestore.bean.Banner;
import com.snaillemon.rookiestore.bean.Category;
import com.snaillemon.rookiestore.bean.Contants;
import com.snaillemon.rookiestore.bean.Page;
import com.snaillemon.rookiestore.bean.Wares;
import com.snaillemon.rookiestore.http.BaseCallback;
import com.snaillemon.rookiestore.http.OkHttpHelper;
import com.snaillemon.rookiestore.http.SpotsCallback;
import com.snaillemon.rookiestore.utils.ToastUtils;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.List;

import static android.R.transition.move;

/**
 * Created by prince on 2016/9/19.
 */
public class CategoryFragment extends BaseFragment{
    private OkHttpHelper mHttpHelper = OkHttpHelper.getInstance();
    @ViewInject(R.id.cate_left_recyclerview)
    private RecyclerView mLeftRecyclerView;
    @ViewInject(R.id.cate_right_recyclerview)
    private RecyclerView mRightRecyclerView;
    @ViewInject(R.id.cate_sliderlayout)
    private SliderLayout mSliderLayout;
    @ViewInject(R.id.cate_refresh)
    private MaterialRefreshLayout mRefreshLayout;
    private CategoryAdapter mCategoryAdapter;
    private int currPage;
    private  static final int STATE_NORMAL=0;
    private  static final int STATE_REFREH=1;
    private  static final int STATE_MORE=2;
    private long category_Id = 1;
    private int curPosition = 0;
    public int state = STATE_NORMAL;
    public List<Banner> mBanners;
    private int pageSize = 10;
    private WaresAdapter mWaresAdapter;
    private int totalPage = 1;
    private LinearLayoutManager mLinearLayoutManager;
    @Override
    public void init() {
        requestCategoryData();
        requestSliderData();
        initRefreshLayout();
    }

    private void initRefreshLayout() {
        mRefreshLayout.setLoadMore(true);
        mRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                refreshData();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                if (currPage <= totalPage) {
                    loadMoreData();
                }else {
                    ToastUtils.show(getContext(),"No More Data");
                    mRefreshLayout.finishRefreshLoadMore();
                }
            }
        });
    }

    private void loadMoreData() {
        currPage++;
        state = STATE_MORE;
        requestWares(category_Id);
    }

    private void refreshData() {
        currPage = 1;
        state = STATE_REFREH;
        requestWares(category_Id);
    }

    private void requestWares(long categoryid) {
        String url = Contants.API.WARES_LIST + "?categoryId=" + categoryid + "&curPage=" + currPage + "&pageSize=" + pageSize;
        mHttpHelper.get(url, new BaseCallback<Page<Wares>>() {
            @Override
            public void onRequestBefore(Request request) {

            }

            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onRepose(Response response) {

            }

            @Override
            public void onSuccess(Response response, Page<Wares> waresPage) {
                currPage = waresPage.getCurrentPage();
                pageSize = waresPage.getPageSize();
                totalPage = waresPage.getTotalPage();
                showWaresData(waresPage.getList());
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }

    private void showWaresData(final List<Wares> wares) {
        switch (state) {
            case STATE_NORMAL:
                if (mWaresAdapter == null) {
                    mWaresAdapter = new WaresAdapter(getContext(), wares);
                    mWaresAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Wares item = wares.get(position);
                            Intent intent = new Intent(getActivity(), WaresDetailsActivity.class);
                            intent.putExtra(Contants.WARE,item);
                            startActivity(intent);
                        }
                    });
                    mRightRecyclerView.setAdapter(mWaresAdapter);
                    mRightRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
                    mRightRecyclerView.setItemAnimator(new DefaultItemAnimator());
                }else {
                    mWaresAdapter.clear();
                    mWaresAdapter.addDatas(wares);
                }
                break;
            case STATE_REFREH:
                mWaresAdapter.clear();
                mWaresAdapter.addDatas(wares);
                mRightRecyclerView.scrollToPosition(0);
                mRefreshLayout.finishRefresh();
                break;
            case STATE_MORE:
                mWaresAdapter.addDatas(mWaresAdapter.getDatas().size(),wares);
                mRightRecyclerView.scrollToPosition(mWaresAdapter.getDatas().size());
                mRefreshLayout.finishRefreshLoadMore();
                break;
        }
    }

    private void requestSliderData() {
        String url = Contants.API.BANNER + "?type=1";
        mHttpHelper.get(url, new SpotsCallback<List<Banner>>(getContext()) {

            @Override
            public void onSuccess(Response response, List<Banner> banners) {
                mBanners = banners;
                initSlider();
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }

    private void initSlider() {
        if (mBanners != null) {
            for (Banner bander : mBanners) {
                DefaultSliderView textSliderView = new DefaultSliderView(getContext());
                textSliderView.image(bander.getImgUrl());
                textSliderView.description(bander.getName());
                textSliderView.setScaleType(BaseSliderView.ScaleType.CenterCrop);
                mSliderLayout.addSlider(textSliderView);
            }
        }
        mSliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mSliderLayout.setCustomAnimation(new DescriptionAnimation());
        mSliderLayout.setPresetTransformer(SliderLayout.Transformer.RotateUp);
        mSliderLayout.setDuration(3000);
    }

    private void requestCategoryData() {
        String url = Contants.API.CATEGORY_LIST;
        mHttpHelper.get(url, new SpotsCallback<List<Category>>(getContext()) {

            @Override
            public void onSuccess(Response response, List<Category> categories) {
                showCategoryData(categories);
                if (categories != null && categories.size() > 0) {
                    requestWares(categories.get(0).getId());
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }

    private void showCategoryData(List<Category> datas) {
        mCategoryAdapter = new CategoryAdapter(getContext(), datas);
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mCategoryAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                int middleCount = mLeftRecyclerView.getChildCount() / 2;
                if (position == curPosition) return;
                Category category = mCategoryAdapter.getItem(position);
                category_Id = category.getId();
                currPage = 1;
                state = STATE_NORMAL;
                requestWares(category_Id);
                curPosition = position;
                mCategoryAdapter.setSelectIndex(curPosition);
                mCategoryAdapter.notifyDataSetChanged();
                if (position > middleCount) {
                    smoothMoveToPosition(position - middleCount,view);
                }else {
                    smoothMoveToPosition(0,view);

                }

            }
        });
        mLeftRecyclerView.setAdapter(mCategoryAdapter);
        mLeftRecyclerView.setLayoutManager(mLinearLayoutManager);
        mLeftRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mLeftRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL_LIST));
    }
    private void smoothMoveToPosition(int n,View view) {
        int firstItem = mLinearLayoutManager.findFirstVisibleItemPosition();
        if (n > firstItem) {
            //scroll up
            mLeftRecyclerView.smoothScrollBy(0,mLeftRecyclerView.getChildAt(n - firstItem).getTop());
        }else if (n <= firstItem){
            //scroll down
            if (n == 0) {
                mLeftRecyclerView.smoothScrollBy(0,-view.getHeight() * (firstItem+1));
            }else {
                mLeftRecyclerView.smoothScrollBy(0,-view.getHeight() * (firstItem - n));
            }
        }
    }
    @Override
    public View createVew(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category,container,false);
    }
}
