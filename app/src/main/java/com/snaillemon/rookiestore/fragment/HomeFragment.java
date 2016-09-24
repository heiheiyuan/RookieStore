package com.snaillemon.rookiestore.fragment;

import android.media.MediaCodecList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.snaillemon.rookiestore.R;
import com.snaillemon.rookiestore.bean.Banner;
import com.snaillemon.rookiestore.http.OkHttpHelper;
import com.snaillemon.rookiestore.http.SpotsCallback;
import com.squareup.okhttp.Response;

import java.util.List;

import static android.os.Build.VERSION_CODES.M;

/**
 * Created by GoodBoy on 2016/9/19.
 */
public class HomeFragment extends BaseFragment{
    @ViewInject(R.id.slider)
    private SliderLayout mSliderLayout;
    private List<Banner> mBanners;
    private OkHttpHelper httpHelper = OkHttpHelper.getInstance();
    @Override
    public View createVew(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home,container,false);
    }
    @Override
    public void init() {
        requestImages();
        initRecyclerView();
    }
    private void initRecyclerView() {

    }
    private void requestImages() {
        String url ="http://112.124.22.238:8081/course_api/banner/query?type=1";
        httpHelper.get(url, new SpotsCallback<List<Banner>>(getContext()) {
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
                TextSliderView textSliderView = new TextSliderView(getContext());
                textSliderView.image(bander.getImgUrl());
                textSliderView.description(bander.getName());
                textSliderView.setScaleType(BaseSliderView.ScaleType.Fit);
                mSliderLayout.addSlider(textSliderView);
            }
        }
        mSliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mSliderLayout.setCustomAnimation(new DescriptionAnimation());
        mSliderLayout.setPresetTransformer(SliderLayout.Transformer.RotateUp);
        mSliderLayout.setDuration(3000);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mSliderLayout.stopAutoCycle();
    }
}
