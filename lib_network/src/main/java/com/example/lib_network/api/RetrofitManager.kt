package com.example.lib_network.api

import com.example.lib_network.api.constants.UrlConstants
import com.example.lib_network.okhttp.client.CommonOkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by yxm on 2019-12-28
 * @function:网络请求
 */
object RetrofitManager {

    private var retrofit = Retrofit.Builder()
            .client(CommonOkHttpClient.getOkHttpClient())
            .baseUrl(UrlConstants.baseUrlKaiYan)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    /**
     * @baseUrl url
     */
    fun getApi(baseUrl: String): Api {
        val retrofit = Retrofit.Builder()
                .client(CommonOkHttpClient.getOkHttpClient())
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        return retrofit.create(Api::class.java)
    }

    //获取接口
    fun getApiService(): Api{
        return retrofit.create(Api::class.java)
    }
}