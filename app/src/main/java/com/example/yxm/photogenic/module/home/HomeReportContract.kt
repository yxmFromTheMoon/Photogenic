package com.example.yxm.photogenic.module.home

import com.example.lib_network.bean.HomeBean
import com.example.yxm.photogenic.base.BaseView
import com.example.yxm.photogenic.base.IPresenter

/**
 *Created by yxm on 2020/2/19
 *@function 首页日报
 */
interface HomeReportContract {

    interface IHomeReportView: BaseView {

        fun setData(data: ArrayList<HomeBean.Issue>)

        fun setMoreData(data: ArrayList<HomeBean.Issue>)

        fun finishRefresh()
    }

    interface IHomeReportPresenter: IPresenter<IHomeReportView> {

        fun getReportData()

        fun loadMoreData()

    }
}