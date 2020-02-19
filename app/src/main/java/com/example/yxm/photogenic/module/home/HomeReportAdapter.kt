package com.example.yxm.photogenic.module.home

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.lib_imageloader.ImageLoaderManager
import com.example.lib_network.bean.HomeBean
import com.example.yxm.photogenic.R
import com.example.yxm.photogenic.utils.TimeHelper
import de.hdodenhof.circleimageview.CircleImageView

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

    override fun convert(helper: BaseViewHolder, item: HomeBean.Issue) {
        //根据viewType渲染不同的item
        with(helper) {
            when (itemViewType) {
                HomeBean.Issue.TEXT_CARD -> {
                    initTextCard(helper, item)
                }
                HomeBean.Issue.FOLLOW_CARD -> {
                    initFollowCard(helper, item)
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
            Glide.with((getView(R.id.video_author_avatar) as CircleImageView).context)
                    .load(item.data.content?.data?.author?.icon?:"")
                    .into(getView(R.id.video_author_avatar) as CircleImageView)
            setText(R.id.video_title, item.data.content?.data?.title)
            setText(R.id.video_duration, TimeHelper.secToTime(item.data.content?.data?.duration
                    ?: 0))
            setText(R.id.video_secondary_title, "#${item.data.content?.data?.category}")
            addOnClickListener(R.id.video_share_iv)
        }
    }
}