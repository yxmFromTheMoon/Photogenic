package com.example.yxm.photogenic.module.categorydetails

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
import com.chad.library.adapter.base.BaseViewHolder
import com.example.lib_imageloader.ImageLoaderManager
import com.example.lib_network.bean.CategoryDetailBean
import com.example.yxm.photogenic.R
import com.example.yxm.photogenic.ui.activity.TagDetailActivity
import com.example.yxm.photogenic.utils.TimeHelper
import de.hdodenhof.circleimageview.CircleImageView
import java.io.Serializable

/**
 *Created by yxm on 2020/2/10
 *@function 分类详情视频adapter
 */
class CategoryVideoAdapter : BaseQuickAdapter<CategoryDetailBean.FollowCardBean, BaseViewHolder>(R.layout.item_category_details_video) {

    override fun convert(helper: BaseViewHolder, item: CategoryDetailBean.FollowCardBean) {
        with(helper) {
            Glide.with(getView(R.id.video_author_avatar) as CircleImageView)
                    .load(item.data.header.icon)
                    .into(getView(R.id.video_author_avatar) as CircleImageView)
            setText(R.id.video_header_description, item.data.header.description)
            setText(R.id.publish_time_tv, "${TimeHelper.timeStamp2Date(item.data.header.time)}发布：")
            setText(R.id.video_header_title, item.data.header.title)
            setText(R.id.video_content_description, item.data.content.data.description)
            /**
             * 标签recyclerView
             */
            val tagAdapter = TagAdapter()
            getView<RecyclerView>(R.id.tags_rv).apply {
                layoutManager = LinearLayoutManager(context, LinearLayout.HORIZONTAL, false)
                adapter = tagAdapter
            }
            tagAdapter.onItemClickListener = OnItemClickListener { adapter, view, position ->
                val tagBean = adapter.getItem(position)
                view.context.startActivity(Intent(view.context, TagDetailActivity::class.java).apply {
                    val bundle = Bundle()
                    bundle.putSerializable("tagBean", tagBean as Serializable)
                    putExtras(bundle)
                })
            }
            tagAdapter.setNewData(item.data.content.data.tags)

            ImageLoaderManager.displayImageForView(getView(R.id.video_picture), item.data.content.data.cover.feed
                    ?: "")
            setText(R.id.video_duration, TimeHelper.secToTime(item.data.content.data.duration))
            setText(R.id.video_like,"${item.data.content.data.consumption.realCollectionCount}")
            setText(R.id.video_comment,"${item.data.content.data.consumption.replyCount}")
            setText(R.id.video_collect,"${item.data.content.data.consumption.collectionCount}")
            addOnClickListener(R.id.video_share_iv)
        }
    }
}