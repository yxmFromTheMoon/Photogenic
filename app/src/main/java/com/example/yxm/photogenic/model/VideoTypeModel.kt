package com.example.yxm.photogenic.model

import com.example.lib_network.api.RetrofitManager
import com.example.lib_network.api.constants.UrlConstants
import com.example.lib_network.bean.CategoryDetailBean
import com.example.lib_network.bean.CommonVideoBean
import com.example.yxm.photogenic.rxschedulers.IoMainScheduler
import io.reactivex.Observable

/**
 * Created by yxm on 2020-1-6
 * @function: 视频类型model
 */
class VideoTypeModel {

    /**
     * 获取不同类型的video
     * @param type 类型ID
     */
    fun getCategoryDetailVideo(type: Int): Observable<CategoryDetailBean>{
        return RetrofitManager.getApi(UrlConstants.baseUrlApiOpen)
                .getVideoRecommend(type)
                .compose(IoMainScheduler())
    }

    //获取相关视频
    fun getRelatedVideoData(id: Long):Observable<CommonVideoBean>{
        return RetrofitManager.getApi(UrlConstants.baseUrlKaiYan)
                .getRelatedVideoData(id)
                .compose(IoMainScheduler())
    }
}