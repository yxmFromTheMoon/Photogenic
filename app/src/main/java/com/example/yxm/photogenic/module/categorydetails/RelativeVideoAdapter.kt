package com.example.yxm.photogenic.module.categorydetails

import android.app.Activity
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
import com.example.lib_network.bean.CommonVideoBean
import com.example.yxm.photogenic.R
import com.example.yxm.photogenic.ui.activity.TagDetailActivity
import com.example.yxm.photogenic.utils.TimeHelper
import de.hdodenhof.circleimageview.CircleImageView
import java.io.Serializable

/**
 *Created by yxm on 2020/2/13
 *@function 标签相关视频adapter
 */
class RelativeVideoAdapter : BaseQuickAdapter<CommonVideoBean.ResultBean, BaseViewHolder>(R.layout.item_category_details_video) {

    override fun convert(helper: BaseViewHolder, item: CommonVideoBean.ResultBean) {
        with(helper) {
            Glide.with(getView(R.id.video_author_avatar) as CircleImageView)
                    .load(item.data.author?.icon ?: "")
                    .into(getView(R.id.video_author_avatar) as CircleImageView)
            setText(R.id.video_header_description, item.data.category)
            setText(R.id.publish_time_tv, "${TimeHelper.timeStamp2Date(item.data.releaseTime)}发布：")
            setText(R.id.video_header_title, item.data.title)
            setText(R.id.video_content_description, item.data.description)
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
                if (view.context is Activity) {
                    view.context.startActivity(Intent(view.context, TagDetailActivity::class.java).apply {
                        val bundle = Bundle()
                        bundle.putSerializable("tagBean", tagBean as Serializable)
                        putExtras(bundle)
                    })
                }
            }
            tagAdapter.setNewData(item.data.tags)

            ImageLoaderManager.displayImageForView(getView(R.id.video_picture), item.data.cover.feed
                    ?: "")
            setText(R.id.video_duration, TimeHelper.secToTime(item.data.duration))
            setText(R.id.video_like, "${item.data.consumption.realCollectionCount}")
            setText(R.id.video_comment, "${item.data.consumption.replyCount}")
            setText(R.id.video_collect, "${item.data.consumption.collectionCount}")
        }
    }
}