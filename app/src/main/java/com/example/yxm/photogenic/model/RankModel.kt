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
     * 获取周、月、历史排行榜
     */
    fun getRankData(strategy: String): Observable<CommonVideoBean>{
        return RetrofitManager.getApi(UrlConstants.baseUrlKaiYan)
                .getRanks(strategy)
                .compose(IoMainScheduler())
    }

}