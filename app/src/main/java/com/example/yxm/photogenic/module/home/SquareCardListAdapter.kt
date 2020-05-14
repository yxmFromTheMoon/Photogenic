package com.example.yxm.photogenic.module.home

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.lib_imageloader.ImageLoaderManager
import com.example.lib_network.bean.CategoryDetailBean
import com.example.yxm.photogenic.R
import com.example.yxm.photogenic.utils.TimeHelper
import com.example.yxm.photogenic.widget.CircleImageView

/**
 *Created by yxm on 2020/2/16
 *@function SquareCardListAdapter
 */
class SquareCardListAdapter: BaseQuickAdapter<CategoryDetailBean.FollowCardBean, BaseViewHolder>(R.layout.item_big_video_bean) {

    override fun convert(holder: BaseViewHolder, item: CategoryDetailBean.FollowCardBean) {
        with(holder) {
            ImageLoaderManager.displayImageForView(getView(R.id.video_picture), item.data.content.data.cover.feed
                    ?: "")
            Glide.with((getView<CircleImageView>(R.id.video_author_avatar)).context)
                    .load(item.data.content.data.author ?.icon ?: "")
                    .into(getView(R.id.video_author_avatar))
            setText(R.id.video_title, item.data.content.data.title)
            setText(R.id.video_duration, TimeHelper.secToTime(item.data.content.data.duration))
            setText(R.id.video_secondary_title, "#${item.data.content.data.category}")
            addChildClickViewIds(R.id.video_share_iv)
        }
    }
}