package com.example.yxm.photogenic.module.discovery

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.lib_imageloader.ImageLoaderManager
import com.example.lib_network.bean.CommonVideoBean
import com.example.yxm.photogenic.R
import com.example.yxm.photogenic.utils.TimeHelper

/**
 *Created by yxm on 2020/2/8
 *@function 发现页面视频adapter
 */
class DiscoveryVideoAdapter: BaseQuickAdapter<CommonVideoBean.ResultBean.ResultData,BaseViewHolder>(R.layout.item_small_video_bean) {

    override fun convert(helper: BaseViewHolder?, item: CommonVideoBean.ResultBean.ResultData) {
        helper?.run {
            ImageLoaderManager.displayImageForView(getView(R.id.video_picture), item.cover.feed ?: "")
            setText(R.id.video_title, item.title)
            setText(R.id.video_duration, TimeHelper.secToTime(item.duration))
            setText(R.id.video_secondary_title, "#${item.category}")
            addOnClickListener(R.id.video_share_iv)
        }
    }
}