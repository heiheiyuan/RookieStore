package com.snaillemon.rookiestore.http;

import android.content.Context;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import dmax.dialog.SpotsDialog;

/**
 * Created by GoodBoy on 9/23/2016.
 */

public abstract class SpotsCallback<T> extends BaseCallback<T> {
    private Context mContext;
    private SpotsDialog mSpotsDialog;

    public SpotsCallback(Context context) {

        this.mContext = context;
        initSpotsDialog();
    }

    private void initSpotsDialog() {
        mSpotsDialog = new SpotsDialog(mContext,"loading...");
    }
    public void showDialog() {
        mSpotsDialog.show();
    }
    public void dismissDialog() {
        mSpotsDialog.dismiss();
    }
    public void setLoadMessage(int resId) {
        mSpotsDialog.setMessage(mContext.getString(resId));
    }
    @Override
    public void onRequestBefore(Request request) {
        showDialog();
    }
    @Override
    public void onFailure(Request request, IOException e) {
        dismissDialog();
    }
    @Override
    public void onRepose(Response response) {
        dismissDialog();
    }
}
