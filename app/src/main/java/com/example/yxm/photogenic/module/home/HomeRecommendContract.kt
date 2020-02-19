package com.example.yxm.photogenic.module.home

import com.example.lib_network.bean.HomeRecommendBean
import com.example.yxm.photogenic.base.BaseView
import com.example.yxm.photogenic.base.IPresenter

/**
 *Created by yxm on 2020/2/15
 *@function 首页推荐
 */
interface HomeRecommendContract {

    interface IHomeRecommendView: BaseView{

        fun setData(data: ArrayList<HomeRecommendBean.Issue>)

        fun setMoreData(data: ArrayList<HomeRecommendBean.Issue>)
    }

    interface IHomeRecommendPresenter: IPresenter<IHomeRecommendView>{

        fun getRecommendData()

        fun loadMoreData()

    }
}