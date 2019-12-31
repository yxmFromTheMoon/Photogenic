package com.example.lib_network.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by yxm on 2019-12-28
 * @function:网络请求
 */
object NetWork {
    private var api: Api? = null
    private val okHttpClient = OkHttpClient()

    val baseUrlKaiYan: String = "http://baobab.kaiyanapp.com/api/"

    val baseUrlApiOpen: String = "https://api.apiopen.top/"
    /**
     * @baseUrl url
     */
    fun getApi(baseUrl: String): Api {
        if(api == null){
            val retrofit = Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
            api = retrofit.create(Api::class.java)
        }
        return api!!
    }

}