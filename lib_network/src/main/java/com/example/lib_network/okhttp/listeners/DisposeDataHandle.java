package com.example.lib_network.okhttp.listeners;

/**
 * Created by yxm on 2019/12/8
 *
 * @function
 */
public class DisposeDataHandle {
    public DisposeDataListener mListener = null;
    public Class<?> mClass = null;
    public String mSource = null; //文件保存的地址

    public DisposeDataHandle(DisposeDataListener listener){
        mListener = listener;
    }

    public DisposeDataHandle(DisposeDataListener listener, Class<?> mClass){
        this.mListener = listener;
        this.mClass = mClass;
    }

    public DisposeDataHandle(DisposeDataListener listener, String source){
        mListener = listener;
        mSource = source;
    }
}
