package com.example.yxm.photogenic.module.discovery

import com.example.lib_network.bean.CategoriesBean
import com.example.lib_network.bean.CommonVideoBean
import com.example.yxm.photogenic.base.BaseView
import com.example.yxm.photogenic.base.IPresenter

/**
 * Created by yxm on 2020-1-14
 * @function: 发现contract
 */
interface DiscoveryContract {
    interface IDiscoveryView: BaseView{

        fun setBannerList(data: ArrayList<String>)

        /**
         * 设置数据
         */
        fun setCategory(data: ArrayList<CategoriesBean>)

        fun setVideoData(data: ArrayList<CommonVideoBean.ResultBean.ResultData>)

        //结束刷新
        fun finishRefresh()
    }

    interface IDiscoveryPresenter: IPresenter<IDiscoveryView>{
        fun getBannerData()

        fun getVideoCollectionWithBrief()

        /**
         * 获取分类数据
         */
        fun getCategoryData()
    }
}