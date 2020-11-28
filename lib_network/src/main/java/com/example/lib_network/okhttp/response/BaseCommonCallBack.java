package com.example.lib_network.okhttp.response;

import android.os.Handler;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by yxm on 2019/12/8
 *
 * @function 回调基类
 */
public abstract class BaseCommonCallBack implements Callback {

    protected final int NETWORK_ERROR = -1;
    protected final int IO_ERROR = -2;
    protected final int OTHER_ERROR = -3;
    protected final int JSON_ERROR = -4;
    protected final String EMPTY_MSG = "";


    protected Handler mDeliveryHandler;
    protected Gson gson = new Gson();

    /**
     * 检查文件是否存在
     * @param localFilePath
     */
    protected void checkLocalFilePath(String localFilePath) {
        File path = new File(localFilePath.substring(0,
                localFilePath.lastIndexOf("/") + 1));
        File file = new File(localFilePath);
        if (!path.exists()) {
            path.mkdirs();
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
