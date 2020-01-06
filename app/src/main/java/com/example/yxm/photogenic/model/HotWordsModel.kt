package com.example.yxm.photogenic.model

import com.example.lib_network.api.RetrofitManager
import com.example.lib_network.api.constants.UrlConstants
import com.example.yxm.photogenic.rxschedulers.IoMainScheduler
import io.reactivex.Observable

/**
 * Created by yxm on 2020-1-6
 * @function: 热门搜索词
 */
class HotWordsModel {

    /**
     * 获取热门搜索词list
     */
    fun getHotWords(): Observable<ArrayList<String>>{
        return RetrofitManager.getApi(UrlConstants.baseUrlKaiYan)
                .getHotWords()
                .compose(IoMainScheduler())
    }
}