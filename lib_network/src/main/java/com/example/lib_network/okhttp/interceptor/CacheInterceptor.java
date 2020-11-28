package com.example.lib_network.okhttp.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by Myron at 2020/11/28 20:08
 *
 * @email yxmbest@163.com
 * @description 缓存拦截器，有网30s内请求同一个接口时读取缓存，
 * 没网直接读取缓存
 */
public class CacheInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        response = response.newBuilder()
                .removeHeader("Cache-Control")
                .removeHeader("Pragma")
                .addHeader("Cache-Control", "max-age" + 30)
                .build();
        return response;
    }
}
