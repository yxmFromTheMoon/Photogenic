package com.example.yxm.photogenic.model

import com.example.lib_network.api.RetrofitManager
import com.example.lib_network.api.constants.UrlConstants
import com.example.lib_network.bean.CategoriesBean
import com.example.yxm.photogenic.rxschedulers.IoMainScheduler
import io.reactivex.Observable
import okhttp3.ResponseBody


/**
 * Created by yxm on 2020-1-7
 * @function: 发现接口model
 */
class DiscoveryModel{

    fun getDiscoveryData(): Observable<ResponseBody>{
        return RetrofitManager.getApi(UrlConstants.baseUrlKaiYan)
                .getDiscovery()
                .compose(IoMainScheduler())
    }

    /**
     * 获取分类目录
     */
    fun getCategoryData(): Observable<ArrayList<CategoriesBean>>{
        return RetrofitManager.getApi(UrlConstants.baseUrlKaiYan)
                .getCategories()
                .compose(IoMainScheduler())
    }
}