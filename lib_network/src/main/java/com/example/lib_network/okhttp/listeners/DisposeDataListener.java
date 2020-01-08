package com.example.lib_network.okhttp.listeners;

/**
 * Created by yxm on 2019/12/8
 *
 * @function 请求回调
 */
public interface DisposeDataListener {

    //成功
    void onSuccess(Object responseObj);

    //失败
    void onFailure(Object reasonObj);
}
