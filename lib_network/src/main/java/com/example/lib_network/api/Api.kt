package com.example.lib_network.api

import com.example.lib_network.bean.CategoriesBean
import com.example.lib_network.bean.CommonVideoBean
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

/**
 *Created by yxm on 2019-12-28
 *@function: api接口
 */
interface Api {

    //获取分类
    @GET("v4/categories")
    fun getCategories(): Observable<ArrayList<CategoriesBean>>

    //获取排行
    @GET("v4/rankList/videos")
    fun getRanks(@Query("strategy")strategy: String): Observable<CommonVideoBean>

    //获取搜索信息
    /**
     * @start 从第几项开始
     * @num 搜索的个数
     * @query 搜索的关键词
     */
    @GET("v1/search?&start=1&num=10")
    fun getSearchInfo(@Query("query")query: String): Observable<CommonVideoBean>

    /**
     * 获取更多信息
     * @param url nextPageUrl
     */
    @GET
    fun getMoreData(@Url url: String): Observable<CommonVideoBean>


    //社区关注
    @GET("v4/tabs/follow")
    fun getCommunityAttention(): Observable<Any>

    //热门搜索词
    @GET("v3/queries/hot")
    fun getHotWords(): Observable<ArrayList<String>>


}