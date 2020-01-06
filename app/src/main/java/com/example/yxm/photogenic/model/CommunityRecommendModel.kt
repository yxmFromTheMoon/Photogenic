package com.example.yxm.photogenic.model

import com.example.lib_network.api.RetrofitManager
import com.example.lib_network.api.constants.UrlConstants
import com.example.lib_network.bean.CommunityRecommendBean
import com.example.yxm.photogenic.rxschedulers.IoMainScheduler
import io.reactivex.Observable

/**
 *Created by yxm on 2020/1/6
 *@function 社区推荐model
 */
class CommunityRecommendModel {

    /**
     * 获取社区推荐数据
     */
    fun getCommunityRecommendData(): Observable<CommunityRecommendBean> {
        return RetrofitManager.getApi(UrlConstants.baseUrlKaiYan)
                .getCommunityRecommend()
                .compose(IoMainScheduler())
    }
}