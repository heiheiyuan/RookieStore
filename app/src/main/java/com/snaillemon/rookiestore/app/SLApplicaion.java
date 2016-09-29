package com.snaillemon.rookiestore.app;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by GoodBoy on 9/28/2016.
 */

public class SLApplicaion extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
