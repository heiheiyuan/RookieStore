package com.snaillemon.rookiestore.http;

import android.os.Handler;
import android.os.Looper;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;
/**
 * Created by GoodBoy on 9/23/2016.
 */
public class OkHttpHelper {
    public static final String TAG = "OkHttpHelper";
    public static OkHttpHelper mInstance;
    private OkHttpClient mHttpClient;
    private Gson mGson;
    private Handler mHandler;
    static {
        mInstance = new OkHttpHelper();
    }
    private OkHttpHelper() {
        mHttpClient = new OkHttpClient();
        mHttpClient.setConnectTimeout(10, TimeUnit.SECONDS);
        mHttpClient.setReadTimeout(10,TimeUnit.SECONDS);
        mHttpClient.setWriteTimeout(30, TimeUnit.SECONDS);
        mGson = new Gson();
        mHandler = new Handler(Looper.getMainLooper());
    }
    public static OkHttpHelper getInstance() {
        return mInstance;
    }
    public void get(String url,BaseCallback callback) {
        Request request = buildGetRequest(url);
        request(request,callback);
    }
    public void post(String url, Map<String,String> params,BaseCallback callback) {
        Request request = buildPostRequest(url,params);
        request(request,callback);
    }
    private void request(final Request request, final BaseCallback callback) {
        callback.onRequestBefore(request);
        mHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                callbackFailure(request,callback,e);
            }
            @Override
            public void onResponse(Response response) throws IOException {
                callbackResponse(callback,response);
                if (response.isSuccessful()) {
                    String resultStr = response.body().string();
                    if (callback.mType == String.class) {
                        callbackSuccess(callback,response,resultStr);
                    }else {
                        try {
                            Object o = mGson.fromJson(resultStr, callback.mType);
                            callbackSuccess(callback,response,o);
                        }catch (JsonParseException e) {
                            callback.onError(response,response.code(),e);
                        }
                    }
                }else {
                    callbackError(callback,response,null);
                }
            }
        });
    }
    private void callbackSuccess(final BaseCallback callback, final Response response, final Object obj) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onSuccess(response,obj);
            }
        });
    }

    private void callbackError(final BaseCallback callback, final Response response, final Exception e) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onError(response,response.code(),e);
            }
        });
    }

    private void callbackResponse(final BaseCallback callback, final Response response) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onRepose(response);
            }
        });
    }

    private void callbackFailure(final Request request, final BaseCallback callback, final IOException e) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onFailure(request,e);
            }
        });
    }

    //return get request
    private Request buildGetRequest(String url) {
        return buildRequest(url,HttpMethodType.GET,null);
    }
    //return post request
    private Request buildPostRequest(String url, Map<String, String> params) {
        return buildRequest(url,HttpMethodType.POST,params);
    }
    private Request buildRequest(String url, HttpMethodType methodType,Map<String,String> params) {
        Request.Builder builder = new Request.Builder().url(url);
        if (methodType == HttpMethodType.GET) {
            builder.get();
        }else if (methodType == HttpMethodType.POST) {
            RequestBody body = buildFromData(params);
            builder.post(body);
        }
        return builder.build();
    }
    //parse data to requestBody
    private RequestBody buildFromData(Map<String, String> params) {
        FormEncodingBuilder builder = new FormEncodingBuilder();
        if (params != null) {
            for (Map.Entry<String,String> entry : params.entrySet()) {
                builder.add(entry.getKey(),entry.getValue());
            }
        }
        return builder.build();
    }
    enum HttpMethodType {
        GET,POST
    }
}
