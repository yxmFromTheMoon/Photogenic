package com.example.yxm.photogenic.model

import com.example.lib_network.api.RetrofitManager
import com.example.lib_network.api.constants.UrlConstants
import com.example.lib_network.bean.CommonVideoBean
import com.example.yxm.photogenic.rxschedulers.IoMainScheduler
import io.reactivex.Observable

/**
 * Created by yxm on 2020-1-6
 * @function: 搜索结果Model
 */
class SearchModel {

    /**
     * 搜索结果
     * @param query 关键字
     */
    fun getSearchData(query: String): Observable<CommonVideoBean>{
        return RetrofitManager.getApi(UrlConstants.baseUrlKaiYan)
                .getSearchInfo(query)
                .compose(IoMainScheduler())
    }

    /**
     * 获取更多搜索信息
     * @param url nextPageUrl
     */
    fun getMoreSearchData(nextPageUrl: String): Observable<CommonVideoBean>{
        return RetrofitManager.getApi(UrlConstants.baseUrlKaiYan)
                .getMoreData(nextPageUrl)
                .compose(IoMainScheduler())
    }

}