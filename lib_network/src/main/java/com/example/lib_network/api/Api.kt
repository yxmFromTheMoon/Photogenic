package com.example.lib_network.api

import com.example.lib_network.bean.*
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.*

/**
 *Created by yxm on 2019-12-28
 *@function: api接口
 */
interface Api {

    //获取分类
    @Headers("domain:kaiyan")
    @GET("v4/categories")
    fun getCategories(): Observable<ArrayList<CategoriesBean>>

    //获取排行
    @Headers("domain:kaiyan")
    @GET("v4/rankList/videos")
    fun getRanks(@Query("strategy")strategy: String): Observable<CommonVideoBean>

    //获取搜索信息
    /**
     * @start 从第几项开始
     * @num 搜索的个数
     * @query 搜索的关键词
     */
    @Headers("domain:kaiyan")
    @GET("v1/search?&start=1&num=10")
    fun getSearchInfo(@Query("query")query: String): Observable<CommonVideoBean>

    /**
     * 获取更多video信息
     * @param url nextPageUrl
     */
    @GET
    @Headers("domain:kaiyan")
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
    @Headers("domain:kaiyan")
    @GET("v3/queries/hot")
    fun getHotWords(): Observable<ArrayList<String>>


    /**
     * 首页推荐
     */
    @GET("v5/index/tab/allRec")
    fun getHomePageRecommend(): Observable<HomeBean>

    /**
     * 首页推荐获取更多
     */
    @GET
    fun getMoreHomePageRecommend(@Url url: String): Observable<HomeBean>

    /**
     * 首页日报
     */
    @GET("v5/index/tab/feed")
    fun getHomePageDailyReport(): Observable<HomeBean>

    /**
     * 首页日报获取更多
     */
    @GET
    fun getMoreHomePageDailyReport(@Url url: String): Observable<HomeBean>

    /**
     * 发现
     */
    @Headers("domain:kaiyan")
    @GET("v5/index/tab/discovery")
    fun getDiscovery(): Observable<ResponseBody>


    /**
     * https://api.apiopen.top/videoCategoryDetails?id=14
     * 视频分类推荐接口
     *
     */
    @Headers("domain:apiopen")
    @GET("videoCategoryDetails?")
    fun getVideoRecommend(@Query("id")id: Int): Observable<CategoryDetailBean>

    /**
     * 根据item id获取相关推荐视频
     * id从视频分类推荐接口中获取
     */
    @Headers("domain:kaiyan")
    @GET("v4/video/related?")
    fun getRelatedVideoData(@Query("id") id: Long): Observable<CommonVideoBean>

}