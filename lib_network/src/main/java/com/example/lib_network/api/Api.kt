package com.example.lib_network.api

import com.example.lib_network.bean.CategoriesBean
import com.example.lib_network.bean.CommonVideoBean
import com.example.lib_network.bean.CommunityFollowBean
import com.example.lib_network.bean.CommunityRecommendBean
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
     * 获取更多video信息
     * @param url nextPageUrl
     */
    @GET
    fun getMoreVideoData(@Url url: String): Observable<CommonVideoBean>

    /**
     * 获取更多关注信息
     * @param url nextPageUrl
     */
    @GET
    fun getMoreFollowData(@Url url: String): Observable<CommunityFollowBean>


    /**
     * 社区关注
     */
    @GET("v4/tabs/follow")
    fun getCommunityFollow(): Observable<CommunityFollowBean>


    /**
     *  社区推荐
     */
    @GET("v5/index/tab/ugcSelected")
    fun getCommunityRecommend(): Observable<CommunityRecommendBean>

    /**
     * 热门搜索词
     */
    @GET("v3/queries/hot")
    fun getHotWords(): Observable<ArrayList<String>>


    /**
     * 首页推荐
     */
    @GET("v5/index/tab/allRec")
    fun getHomePageRecommend(): Observable<Any>

    /**
     * 首页日报
     */
    @GET("v5/index/tab/feed")
    fun getHomePageDailyReport(): Observable<Any>


    /**
     * https://api.apiopen.top/videoCategoryDetails?id=14
     * 视频分类推荐接口
     *
     */
    @GET("videoCategoryDetails")
    fun getVideoRecommend(@Query("id")id: Int): Observable<Any>


    /**
     * http://baobab.kaiyanapp.com/api/v5/index/tab/discovery
     * 发现模块
     * 视频分类推荐接口
     */
    @GET("v5/index/tab/discovery")
    fun getDiscovery(): Observable<Any>

}