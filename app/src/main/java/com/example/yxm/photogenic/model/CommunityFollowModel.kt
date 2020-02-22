package com.example.yxm.photogenic.model

import com.example.lib_network.api.RetrofitManager
import com.example.lib_network.api.constants.UrlConstants
import com.example.lib_network.bean.CommunityFollowBean
import com.example.yxm.photogenic.rxschedulers.IoMainScheduler
import io.reactivex.Observable

/**
 * Created by yxm on 2020-1-6
 * @function: 社区关注model
 */
class CommunityFollowModel {

    /**
     * 获取社区关注数据
     */
    fun getCommunityFollowData(): Observable<CommunityFollowBean>{
        return RetrofitManager.getApi(UrlConstants.baseUrlKaiYan)
                .getCommunityFollow()
                .compose(IoMainScheduler())
    }

    /**
     * 获取更多关注数据
     *@param nextPageUrl
     */
    fun getMoreCommunityFollowData(nextPageUrl: String): Observable<CommunityFollowBean>{
        return RetrofitManager.getApi(UrlConstants.baseUrlKaiYan)
                .getMoreCommunityFollow(nextPageUrl)
                .compose(IoMainScheduler())
    }

}