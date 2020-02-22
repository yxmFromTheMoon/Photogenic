package com.example.yxm.photogenic.module.community

import com.example.lib_network.bean.CommonVideoBean
import com.example.lib_network.bean.CommunityFollowBean
import com.example.yxm.photogenic.base.BaseView
import com.example.yxm.photogenic.base.IPresenter

/**
 *Created by yxm on 2020/2/20
 *@function 社区关注contract
 */
interface CommunityFollowContract {

    interface ICommunityFollowView: BaseView {

        fun setData(data: ArrayList<CommonVideoBean.ResultBean>)

        fun setMoreData(data: ArrayList<CommonVideoBean.ResultBean>)

        fun finishRefresh()
    }

    interface ICommunityFollowPresenter: IPresenter<ICommunityFollowView> {

        fun getFollowData()

        fun loadMoreData()
    }
}