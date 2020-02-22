package com.example.yxm.photogenic.module.community

import com.example.lib_network.bean.CommunityBean
import com.example.yxm.photogenic.base.BaseView
import com.example.yxm.photogenic.base.IPresenter

/**
 *Created by yxm on 2020/2/20
 *@function 社区关注contract
 */
interface CommunityRecommendContract {
    interface ICommunityRecommendView: BaseView{

        fun setData(data: ArrayList<CommunityBean.Issue>)

        fun setMoreData(data: ArrayList<CommunityBean.Issue>)

        fun finishRefresh()
    }

    interface ICommunityRecommendPresenter: IPresenter<ICommunityRecommendView>{

        fun getRecommendData()

        fun loadMoreData()
    }
}