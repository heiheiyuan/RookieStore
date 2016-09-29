package com.snaillemon.rookiestore.http;

import com.google.gson.internal.$Gson$Types;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by GoodBoy on 9/23/2016.
 */
public abstract class BaseCallback<T> {
    public Type mType;
    static Type getSupercallTypeParameter(Class<?> subclass){
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type paramter");
        }
        ParameterizedType parameterizedType = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterizedType.getActualTypeArguments()[0]);
    }
    public BaseCallback() {
        mType = getSupercallTypeParameter(getClass());
    }
    public abstract void onRequestBefore(Request request);
    public abstract void onFailure(Request request, IOException e);
    /**
     *call back this method when request successful
     * @param response
     */
    public abstract void onRepose(Response response);

    /**
     * call back this method when reponse code is [200,300]
     * @param response
     * @param t
     */
    public abstract void onSuccess(Response response,T t);

    /**
     * call back this method when reponse code is 400 403 404 500
     * @param response
     * @param code
     * @param e
     */
    public abstract void onError(Response response,int code,Exception e);
}
