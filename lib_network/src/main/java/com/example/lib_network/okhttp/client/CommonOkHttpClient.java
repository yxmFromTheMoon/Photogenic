package com.example.lib_network.okhttp.client;

import com.example.lib_network.api.constants.ChangeBaseUrlInterceptor;
import com.example.lib_network.okhttp.cookie.SimpleCookieJar;
import com.example.lib_network.okhttp.https.HttpsUtils;
import com.example.lib_network.okhttp.listeners.DisposeDataHandle;
import com.example.lib_network.okhttp.response.CommonFileCallBack;
import com.example.lib_network.okhttp.response.CommonJsonCallBack;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by yxm on 2019/12/8
 *
 * @function 网络请求客户端
 */
public class CommonOkHttpClient {

    private static final int TIME_OUT = 30;
    private static OkHttpClient mOkHttpClient;

    static {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
        okHttpClientBuilder.cookieJar(new SimpleCookieJar())
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                //.addInterceptor(new ChangeBaseUrlInterceptor())
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
                .followRedirects(true)
                .retryOnConnectionFailure(true)
                /**
                 * trust all the https point
                 */
                .sslSocketFactory(HttpsUtils.initSSLSocketFactory(),
                        HttpsUtils.initTrustManager());

        mOkHttpClient = okHttpClientBuilder.build();
    }

    public static OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    /**
     * get请求
     *
     * @return
     */
    public static Call get(Request request, DisposeDataHandle handle) {
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new CommonJsonCallBack(handle));
        return call;
    }

    /**
     * post请求
     *
     * @param request
     * @param handle
     * @return
     */
    public static Call post(Request request, DisposeDataHandle handle) {
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new CommonJsonCallBack(handle));
        return call;
    }

    /**
     * 文件上传下载
     *
     * @param request
     * @param handle
     * @return
     */
    public static Call downloadFile(Request request, DisposeDataHandle handle) {
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new CommonFileCallBack(handle));
        return call;
    }
}
