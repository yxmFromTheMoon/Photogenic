package com.example.yxm.photogenic.module.community

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.lib_imageloader.ImageLoaderManager
import com.example.lib_network.bean.CommonVideoBean
import com.example.yxm.photogenic.R
import com.example.yxm.photogenic.utils.TimeHelper
import de.hdodenhof.circleimageview.CircleImageView

/**
 *Created by yxm on 2020/2/22
 *@function 社区关注adapter
 */
class CommunityFollowAdapter : BaseQuickAdapter<CommonVideoBean.ResultBean, BaseViewHolder>(R.layout.item_category_details_video) {

    override fun convert(helper: BaseViewHolder, item: CommonVideoBean.ResultBean) {
        with(helper) {
            setGone(R.id.tags_rv, false)
            Glide.with(getView(R.id.video_author_avatar) as CircleImageView)
                    .load(item.data.author?.icon ?: "")
                    .into(getView(R.id.video_author_avatar) as CircleImageView)
            setText(R.id.video_header_description, item.data.category)
            setText(R.id.publish_time_tv, "${TimeHelper.timeStamp2Date(item.data.releaseTime)}发布：")
            setText(R.id.video_header_title, item.data.title)
            setText(R.id.video_content_description, item.data.description)

            ImageLoaderManager.displayImageForView(getView(R.id.video_picture), item.data.cover.feed
                    ?: "")
            setText(R.id.video_duration, TimeHelper.secToTime(item.data.duration))
            setText(R.id.video_like, "${item.data.consumption.realCollectionCount}")
            setText(R.id.video_comment, "${item.data.consumption.replyCount}")
            setText(R.id.video_collect, "${item.data.consumption.collectionCount}")
            addOnClickListener(R.id.video_share_iv)
        }
    }
}