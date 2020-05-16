package com.example.yxm.photogenic.module.home

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.lib_imageloader.ImageLoaderManager
import com.example.lib_network.bean.HomeBean
import com.example.yxm.photogenic.R
import com.example.yxm.photogenic.utils.TimeHelper

/**
 *Created by yxm on 2020/2/19
 *@function 首页日报adapter
 */
class HomeReportAdapter(data: ArrayList<HomeBean.Issue>) : BaseMultiItemQuickAdapter<HomeBean.Issue, BaseViewHolder>(data) {
    //添加不同的布局
    init {
        addItemType(HomeBean.Issue.TEXT_CARD, R.layout.item_text_card)
        addItemType(HomeBean.Issue.FOLLOW_CARD, R.layout.item_big_video_bean)
    }

    override fun convert(holder: BaseViewHolder, item: HomeBean.Issue) {
        //根据viewType渲染不同的item
        with(holder) {
            when (itemViewType) {
                HomeBean.Issue.TEXT_CARD -> {
                    initTextCard(holder, item)
                }
                HomeBean.Issue.FOLLOW_CARD -> {
                    initFollowCard(holder, item)
                }
            }
        }
    }

    /**
     * 渲染textCard类型item
     */
    private fun initTextCard(helper: BaseViewHolder, item: HomeBean.Issue) {
        with(helper) {
            setText(R.id.text_card_title, item.data.text)
        }
    }

    /**
     * 渲染followCard类型item
     */
    private fun initFollowCard(helper: BaseViewHolder, item: HomeBean.Issue) {
        with(helper) {
            ImageLoaderManager.displayImageForView(getView(R.id.video_picture), item.data.content?.data?.cover?.feed
                    ?: "")
            ImageLoaderManager.displayImageForView(getView(R.id.video_author_avatar),
                    item.data.content?.data?.author?.icon ?: "")
            setText(R.id.video_title, item.data.content?.data?.title)
            setText(R.id.video_duration, TimeHelper.secToTime(item.data.content?.data?.duration
                    ?: 0))
            setText(R.id.video_secondary_title, "#${item.data.content?.data?.category}")
            addChildClickViewIds(R.id.video_share_iv)
        }
    }
}