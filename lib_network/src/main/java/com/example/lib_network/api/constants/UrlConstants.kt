package com.example.lib_network.api.constants

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response

/**
 *Created by yxm on 2019/12/31
 *@function base Url
 */
object UrlConstants {

    const val baseUrlKaiYan: String = "http://baobab.kaiyanapp.com/api/"
    const val baseUrlApiOpen: String = "https://api.apiopen.top/"
    const val KAIYAN = "kaiyan"
    const val APIOPEN = "apiopen"
    const val DOMAIN = "domain"
}

class ChangeBaseUrlInterceptor: Interceptor{

    /**
     * 重写拦截器
     */
    override fun intercept(chain: Interceptor.Chain): Response {
        //获取request
        val request = chain.request()
        //获取原有httpUrl
        val oldHttpUrl = request.url()

        val builder = request.newBuilder()

        val headerValues = request.header(UrlConstants.DOMAIN)

        if (headerValues != null && headerValues.isNotEmpty()){

            builder.removeHeader(UrlConstants.DOMAIN)

            val headValue = headerValues[0]

            //切换新的baseUrl
            val newBaseUrl: HttpUrl? = when(headValue.toString()){

                UrlConstants.KAIYAN -> {
                    HttpUrl.parse(UrlConstants.baseUrlKaiYan)
                }
                UrlConstants.APIOPEN -> {
                    HttpUrl.parse(UrlConstants.baseUrlApiOpen)
                }
                else -> {
                    HttpUrl.parse(UrlConstants.baseUrlKaiYan)
                }
            }
            newBaseUrl?.run {
                val newHttpUrl = oldHttpUrl.newBuilder()
                        .scheme(scheme()).host(host())
                        .port(port()).build()
                return chain.proceed(builder.url(newHttpUrl).build())
            }
        }
        return chain.proceed(request)
    }
}