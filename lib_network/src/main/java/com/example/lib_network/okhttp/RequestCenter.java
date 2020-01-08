package com.example.lib_network.okhttp;

import com.example.lib_network.okhttp.client.CommonOkHttpClient;
import com.example.lib_network.okhttp.listeners.DisposeDataHandle;
import com.example.lib_network.okhttp.listeners.DisposeDataListener;
import com.example.lib_network.okhttp.request.CommonRequest;
import com.example.lib_network.okhttp.request.RequestParams;

/**
 * Created by yxm on 2020/1/8
 *
 * @function 请求中心
 */
public class RequestCenter {

    //根据参数发送所有get请求
    public static void getRequest(String url, RequestParams params, DisposeDataListener listener,
                                  Class<?> clazz) {
        CommonOkHttpClient.get(CommonRequest.
                createGetRequest(url, params), new DisposeDataHandle(listener, clazz));
    }

    //根据参数发送所有post请求
    public static void postRequest(String url, RequestParams params, DisposeDataListener listener,
                                  Class<?> clazz) {
        CommonOkHttpClient.post(CommonRequest.
                createPostRequest(url, params), new DisposeDataHandle(listener, clazz));
    }
}
