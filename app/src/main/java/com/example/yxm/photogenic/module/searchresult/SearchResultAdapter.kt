package com.example.yxm.photogenic.module.searchresult

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.lib_imageloader.ImageLoaderManager
import com.example.lib_network.bean.CommonVideoBean
import com.example.yxm.photogenic.R
import com.example.yxm.photogenic.utils.TimeHelper
import de.hdodenhof.circleimageview.CircleImageView

/**
 * Created by yxm on 2020-1-17
 * @function: 搜索结果adapter
 */
class SearchResultAdapter : BaseQuickAdapter<CommonVideoBean.ResultBean, BaseViewHolder>(R.layout.item_big_video_bean) {

    override fun convert(helper: BaseViewHolder, item: CommonVideoBean.ResultBean) {
        with(helper) {
            ImageLoaderManager.displayImageForView(getView(R.id.video_picture), item.data.cover.feed
                    ?: "")
            Glide.with((getView(R.id.video_author_avatar) as CircleImageView).context)
                    .load(item.data.author?.icon?:"")
                    .into(getView(R.id.video_author_avatar) as CircleImageView)
            setText(R.id.video_title, item.data.title)
            setText(R.id.video_duration, TimeHelper.secToTime(item.data.duration))
            setText(R.id.video_secondary_title, "#${item.data.category}")
            addOnClickListener(R.id.video_share_iv)
        }
    }
}