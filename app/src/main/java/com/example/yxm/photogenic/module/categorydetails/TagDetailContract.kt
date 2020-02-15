package com.example.yxm.photogenic.module.categorydetails

import com.example.lib_network.bean.CommonVideoBean
import com.example.yxm.photogenic.base.BaseView
import com.example.yxm.photogenic.base.IPresenter

/**
 *Created by yxm on 2020/2/13
 *@function 标签详情contract
 */
interface TagDetailContract {
    interface ITagDetailView: BaseView{
        fun setVideo(data: ArrayList<CommonVideoBean.ResultBean>)

        fun finishRefresh()
    }

    interface ITagDetailPresenter: IPresenter<ITagDetailView>{
        fun getTagVideo(id: Long)
    }
}