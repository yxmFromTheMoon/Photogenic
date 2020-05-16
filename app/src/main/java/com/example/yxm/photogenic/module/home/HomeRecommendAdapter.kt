package com.example.yxm.photogenic.module.home

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.lib_imageloader.ImageLoaderManager
import com.example.lib_network.bean.CategoryDetailBean
import com.example.lib_network.bean.HomeBean
import com.example.yxm.photogenic.R
import com.example.yxm.photogenic.ui.activity.CategoryDetailActivity
import com.example.yxm.photogenic.ui.activity.VideoPlayActivity
import com.example.yxm.photogenic.utils.TimeHelper
import share.core.ShareDialog

/**
 *Created by yxm on 2020/2/16
 *@function 首页推荐adapter
 */
class HomeRecommendAdapter(data: ArrayList<HomeBean.Issue>) : BaseMultiItemQuickAdapter<HomeBean.Issue, BaseViewHolder>(data) {

    //添加不同的布局
    init {
        addItemType(HomeBean.Issue.SQUARE_CARD, R.layout.item_square_card)
        addItemType(HomeBean.Issue.TEXT_CARD, R.layout.item_text_card)
        addItemType(HomeBean.Issue.FOLLOW_CARD, R.layout.item_small_video_bean)
        addItemType(HomeBean.Issue.VIDEO_CARD, R.layout.item_small_video_bean)
    }

    override fun convert(holder: BaseViewHolder, item: HomeBean.Issue) {
        //根据viewType渲染不同的item
        with(holder) {
            when (itemViewType) {
                HomeBean.Issue.SQUARE_CARD -> {
                    initSquareCard(holder, item)
                }
                HomeBean.Issue.TEXT_CARD -> {
                    initTextCard(holder, item)
                }
                HomeBean.Issue.FOLLOW_CARD -> {
                    initFollowCard(holder, item)
                }
                HomeBean.Issue.VIDEO_CARD -> {
                    initVideoCard(holder, item)
                }
            }
        }
    }

    /**
     * 渲染squareCard类型item
     */
    private fun initSquareCard(helper: BaseViewHolder, item: HomeBean.Issue) {
        with(helper) {
            setText(R.id.square_card_title, item.data.header?.title)
            val squareCardListAdapter = SquareCardListAdapter()
            getView<RecyclerView>(R.id.square_card_rv).apply {
                layoutManager = LinearLayoutManager(context)
                adapter = squareCardListAdapter
            }
            squareCardListAdapter.setOnItemClickListener{ adapter, _, position ->
                val bean = adapter.getItem(position) as CategoryDetailBean.FollowCardBean
                val bundle = Bundle().apply {
                    putSerializable("video", bean.data.content.data)
                    putLong("relativeVideoId", bean.data.content.data.id)
                    putInt("fromWhere", CategoryDetailActivity.CATEGORY_DETAIL)
                }
                context.startActivity(Intent(context, VideoPlayActivity::class.java).apply {
                    putExtras(bundle)
                })
            }
            squareCardListAdapter.setOnItemChildClickListener { adapter, view, position ->
                val followBean = adapter.getItem(position) as CategoryDetailBean.FollowCardBean
                when (view.id) {
                    //分享链接
                    R.id.video_share_iv -> {
                        val dialog = ShareDialog(context)
                        dialog.apply {
                            mShareText = followBean.data.content.data.description
                            mShareTitle = followBean.data.content.data.title
                            mShareImageUrl = followBean.data.content.data.cover.feed ?: ""
                            mUrl = followBean.data.content.data.webUrl.raw
                            show()
                        }
                    }
                }
            }
            squareCardListAdapter.setList(item.data.itemList)
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
            setText(R.id.video_title, item.data.content?.data?.title)
            setText(R.id.video_duration, TimeHelper.secToTime(item.data.content?.data?.duration
                    ?: 0))
            setText(R.id.video_secondary_title, "#${item.data.content?.data?.category}")
            addChildClickViewIds(R.id.video_share_iv)
        }
    }

    /**
     * 渲染videoCard类型item
     */
    private fun initVideoCard(helper: BaseViewHolder, item: HomeBean.Issue) {
        with(helper) {
            ImageLoaderManager.displayImageForView(getView(R.id.video_picture), item.data.cover?.feed
                    ?: "")
            setText(R.id.video_title, item.data.title)
            setText(R.id.video_duration, TimeHelper.secToTime(item.data.duration
                    ?: 0))
            setText(R.id.video_secondary_title, "#${item.data.category}")
            addChildClickViewIds(R.id.video_share_iv)
        }
    }
}