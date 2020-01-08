package com.example.lib_network.okhttp.response;

import android.os.Handler;
import android.os.Looper;

import com.example.lib_network.exception.OkHttpException;
import com.example.lib_network.okhttp.listeners.DisposeDataHandle;
import com.example.lib_network.okhttp.listeners.DisposeDataListener;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by yxm on 2019/12/8
 *
 * @function 处理json类型的响应
 */
public class CommonJsonCallBack extends BaseCommonCallBack{

    private DisposeDataListener mListener;
    private Class<?> mClass;

    public CommonJsonCallBack(DisposeDataHandle handle){
        this.mListener = handle.mListener;
        this.mClass = handle.mClass;
        mDeliveryHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void onFailure(Call call, final IOException e) {
        mDeliveryHandler.post(new Runnable() {
            @Override
            public void run() {
                mListener.onFailure(new OkHttpException(NETWORK_ERROR,e));
            }
        });
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        final String result = response.body().string();
        mDeliveryHandler.post(new Runnable() {
            @Override
            public void run() {
                handleResponse(result);
            }
        });
    }

    //处理返回的数据
    private void handleResponse(String result){

        if(result == null || result.trim().equals("")){
            mListener.onFailure(new OkHttpException(NETWORK_ERROR,EMPTY_MSG));
        }

        try{
            if(mClass == null){
                mListener.onSuccess(result);
            }else {
                Object obj = gson.fromJson(result,mClass);
                if(obj != null){
                    mListener.onSuccess(obj);
                }else {
                    mListener.onFailure(new OkHttpException(JSON_ERROR,EMPTY_MSG));
                }
            }
        }catch (Exception e){
            mListener.onFailure(new OkHttpException(JSON_ERROR,EMPTY_MSG));
        }
    }
}
