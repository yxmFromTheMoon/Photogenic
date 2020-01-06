package com.example.yxm.photogenic.model

import com.example.lib_network.api.RetrofitManager
import com.example.lib_network.api.constants.UrlConstants
import com.example.lib_network.bean.CommonVideoBean
import com.example.yxm.photogenic.rxschedulers.IoMainScheduler
import io.reactivex.Observable

/**
 * Created by yxm on 2020-1-6
 * @function: 获取排行数据
 */
class RankModel {

    /**
     * 获取周排行榜
     */
    fun getWeeklyData(): Observable<CommonVideoBean>{
        return RetrofitManager.getApi(UrlConstants.baseUrlKaiYan)
                .getRanks("weekly")
                .compose(IoMainScheduler())
    }

    /**
     * 获取月排行榜
     */
    fun getMonthlyData(): Observable<CommonVideoBean>{
        return RetrofitManager.getApi(UrlConstants.baseUrlKaiYan)
                .getRanks("monthly")
                .compose(IoMainScheduler())
    }


    /**
     * 获取总排行榜
     */
    fun getHistoricalData(): Observable<CommonVideoBean>{
        return RetrofitManager.getApi(UrlConstants.baseUrlKaiYan)
                .getRanks("historical")
                .compose(IoMainScheduler())
    }

}