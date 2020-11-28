package com.example.lib_network.okhttp.interceptor;

import com.example.lib_network.okhttp.listeners.UploadFileProgressListener;

import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;

/**
 * Created by Myron at 2020/11/28 20:09
 *
 * @email yxmbest@163.com
 * @description 静态代理模式实现文件上传进度监听
 */
public class ExMultipartBody extends RequestBody {
    private UploadFileProgressListener mProgressListener;
    private MultipartBody mRequestBody;
    private long mCurrentLength;

    public ExMultipartBody(MultipartBody requestBody) {
        mRequestBody = requestBody;
    }

    public ExMultipartBody(MultipartBody requestBody, UploadFileProgressListener listener) {
        mRequestBody = requestBody;
        mProgressListener = listener;
    }

    @Override
    public MediaType contentType() {
        return mRequestBody.contentType();
    }

    @Override
    public void writeTo(final BufferedSink sink) throws IOException {
        //在这里实现监听
        final long contentLength = contentLength();
        //获取当前写了多少数据
        //还是代理模式，最终调用的还是sink的write方法
        ForwardingSink forwardingSink = new ForwardingSink(sink) {
            @Override
            public void write(Buffer source, long byteCount) throws IOException {
                mCurrentLength+=byteCount;
                if(mProgressListener != null){
                    mProgressListener.onProgress(contentLength,mCurrentLength);
                }
                super.write(source, byteCount);
            }
        };
        BufferedSink bufferedSink = Okio.buffer(forwardingSink);
        mRequestBody.writeTo(bufferedSink);
        //刷新
        bufferedSink.flush();
    }
}