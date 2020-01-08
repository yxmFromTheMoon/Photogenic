package com.example.lib_network.okhttp.listeners;

/**
 * Created by yxm on 2019/12/8
 *
 * @function 监听下载进度
 */
public interface DisposeDownLoadListener extends DisposeDataListener {

    void onProgress(int progress);
}
