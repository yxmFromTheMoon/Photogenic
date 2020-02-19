package com.example.yxm.photogenic.model

import com.example.lib_network.api.RetrofitManager
import com.example.lib_network.api.constants.UrlConstants
import com.example.lib_network.bean.HomeBean
import com.example.yxm.photogenic.rxschedulers.IoMainScheduler
import io.reactivex.Observable

/**
 *Created by yxm on 2020/2/15
 *@function 首页model
 */
class HomeModel {

    /**
     * 首页推荐
     */
    fun getHomeRecommend(): Observable<HomeBean>{
        return RetrofitManager.getApi(UrlConstants.baseUrlKaiYan)
                .getHomePageRecommend()
                .compose(IoMainScheduler())
    }

    fun getMoreHomeRecommend(nextPageUrl: String): Observable<HomeBean>{
        return RetrofitManager.getApi(UrlConstants.baseUrlKaiYan)
                .getMoreHomePageRecommend(nextPageUrl)
                .compose(IoMainScheduler())
    }

    /**
     * 首页日报
     */
    fun getHomeReport(): Observable<HomeBean>{
        return RetrofitManager.getApi(UrlConstants.baseUrlKaiYan)
                .getHomePageDailyReport()
                .compose(IoMainScheduler())
    }

    fun getMoreHomeReport(nextPageUrl: String): Observable<HomeBean>{
        return RetrofitManager.getApi(UrlConstants.baseUrlKaiYan)
                .getMoreHomePageDailyReport(nextPageUrl)
                .compose(IoMainScheduler())
    }

}