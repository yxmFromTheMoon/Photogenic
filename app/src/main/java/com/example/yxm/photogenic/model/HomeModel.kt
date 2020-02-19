package com.example.yxm.photogenic.model

import com.example.lib_network.api.RetrofitManager
import com.example.lib_network.api.constants.UrlConstants
import com.example.lib_network.bean.HomeRecommendBean
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
    fun getHomeRecommend(): Observable<HomeRecommendBean>{
        return RetrofitManager.getApi(UrlConstants.baseUrlKaiYan)
                .getHomePageRecommend()
                .compose(IoMainScheduler())
    }

    fun getMoreHomeRecommend(nextPageUrl: String): Observable<HomeRecommendBean>{
        return RetrofitManager.getApi(UrlConstants.baseUrlKaiYan)
                .getMoreHomePageRecommend(nextPageUrl)
                .compose(IoMainScheduler())
    }

}