package com.example.yxm.photogenic.module.videodetail

import android.annotation.TargetApi
import android.os.Build
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.lib_imageloader.ImageLoaderManager
import com.example.lib_network.bean.CommonVideoBean
import com.example.yxm.photogenic.R
import com.example.yxm.photogenic.utils.TimeHelper

/**
 *Created by yxm on 2020/2/25
 *@function 播放页面相关视频adapter
 */
class VideoDetailRelativeAdapter : BaseQuickAdapter<CommonVideoBean.ResultBean, BaseViewHolder>(R.layout.item_small_video_bean) {

    @TargetApi(Build.VERSION_CODES.M)
    override fun convert(holder: BaseViewHolder, item: CommonVideoBean.ResultBean) {
        holder.run {
            ImageLoaderManager.displayImageForView(getView(R.id.video_picture), item.data.cover.feed
                    ?: "")
            setText(R.id.video_title, item.data.title)
            setTextColor(R.id.video_title,context.resources.getColor(R.color.white,null))
            setText(R.id.video_duration, TimeHelper.secToTime(item.data.duration))
            setText(R.id.video_secondary_title, "#${item.data.category}")
            setTextColor(R.id.video_secondary_title,context.resources.getColor(R.color.white,null))
            setImageResource(R.id.video_share_iv,R.drawable.ic_action_share_without_padding)
            addChildClickViewIds(R.id.video_share_iv)
        }
    }
}