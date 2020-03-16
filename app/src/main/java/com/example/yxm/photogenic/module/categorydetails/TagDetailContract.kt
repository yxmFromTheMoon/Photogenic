package com.example.yxm.photogenic.module.categorydetails

import com.example.lib_network.bean.CategoriesBean
import com.example.lib_network.bean.CategoryDetailBean
import com.example.lib_network.bean.CommonVideoBean
import com.example.lib_network.bean.HomeBean
import com.example.yxm.photogenic.base.BaseView
import com.example.yxm.photogenic.base.IPresenter

/**
 *Created by yxm on 2020/2/13
 *@function 标签详情contract
 */
interface TagDetailContract {
    interface ITagDetailView : BaseView {

        fun finishRefresh()

        fun setTagVideo(data: ArrayList<CategoryDetailBean.FollowCardBean>)

        fun loadMoreVideo(data: ArrayList<CategoryDetailBean.FollowCardBean>)
    }

    interface ITagDetailPresenter : IPresenter<ITagDetailView> {
        fun getTagsVideo(id: Long)

        fun loadMoreVideo()
    }
}