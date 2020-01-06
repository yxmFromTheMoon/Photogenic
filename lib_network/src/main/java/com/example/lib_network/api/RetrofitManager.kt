package com.example.lib_network.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by yxm on 2019-12-28
 * @function:网络请求
 */
object RetrofitManager {
    private var api: Api? = null

    /**
     * @baseUrl url
     */
    fun getApi(baseUrl: String): Api {
        if(api == null){
            val retrofit = Retrofit.Builder()
                    .client(getOkHttpClient())
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
            api = retrofit.create(Api::class.java)
        }
        return api!!
    }

    /**
     * 获取okHttpClient
     */
    private fun getOkHttpClient(): OkHttpClient{
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .connectTimeout(60L,TimeUnit.SECONDS)
                .readTimeout(60L,TimeUnit.SECONDS)
                .writeTimeout(60L,TimeUnit.SECONDS)
                .build()
    }

}