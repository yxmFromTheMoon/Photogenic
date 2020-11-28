package com.example.lib_network.okhttp.listeners;

/**
 * Created by Myron at 2020/11/28 20:14
 * @email yxmbest@163.com
 * @description
 */
public interface UploadFileProgressListener {

    void onProgress(long total,float currentProgress);
}
