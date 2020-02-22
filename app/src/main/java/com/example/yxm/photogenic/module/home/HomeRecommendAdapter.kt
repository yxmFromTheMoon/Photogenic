package com.example.yxm.photogenic.module.home

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
import com.chad.library.adapter.base.BaseViewHolder
import com.example.lib_imageloader.ImageLoaderManager
import com.example.lib_network.bean.HomeBean
import com.example.lib_share.share.ShareDialog
import com.example.yxm.photogenic.R
import com.example.yxm.photogenic.utils.TimeHelper
import es.dmoral.toasty.Toasty

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

    override fun convert(helper: BaseViewHolder, item: HomeBean.Issue) {
        //根据viewType渲染不同的item
        with(helper) {
            when (itemViewType) {
                HomeBean.Issue.SQUARE_CARD -> {
                    initSquareCard(helper, item)
                }
                HomeBean.Issue.TEXT_CARD -> {
                    initTextCard(helper, item)
                }
                HomeBean.Issue.FOLLOW_CARD -> {
                    initFollowCard(helper, item)
                }
                HomeBean.Issue.VIDEO_CARD -> {
                    initVideoCard(helper, item)
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
            squareCardListAdapter.onItemClickListener = OnItemClickListener { adapter, view, position ->
                val squareCardBean = adapter.getItem(position)
                Toasty.error(mContext, "跳转播放页")
            }
            squareCardListAdapter.onItemChildClickListener = OnItemChildClickListener { adapter, view, position ->
                when (view.id) {
                    R.id.video_share_iv -> {
                        val dialog = ShareDialog(mContext)
                        dialog.show()
                    }
                }
            }
            squareCardListAdapter.setNewData(item.data.itemList)
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
            addOnClickListener(R.id.video_share_iv)
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
            addOnClickListener(R.id.video_share_iv)
        }
    }
}