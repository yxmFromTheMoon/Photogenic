package com.example.yxm.photogenic.module.categorydetails

import com.example.lib_network.bean.CategoryDetailBean
import com.example.yxm.photogenic.base.BaseView
import com.example.yxm.photogenic.base.IPresenter

/**
 *Created by yxm on 2020/2/12
 *@function 视频详情contract
 */
interface CategoryDetailContract {

    interface ICategoryDetailView: BaseView{

        fun setVideo(data: ArrayList<CategoryDetailBean.FollowCardBean>)

        fun setMoreVideo(data: ArrayList<CategoryDetailBean.FollowCardBean>)

        fun finishRefresh()
    }

    interface ICategoryDetailPresenter: IPresenter<ICategoryDetailView>{

        fun getVideo(id: Int)

        fun loadMoreVideo(id: Long)
    }
}