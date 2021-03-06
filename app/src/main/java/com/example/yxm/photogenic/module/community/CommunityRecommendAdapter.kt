package com.example.yxm.photogenic.module.community

import android.widget.FrameLayout
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.lib_imageloader.ImageLoaderManager
import com.example.lib_network.bean.CommunityBean
import com.example.yxm.photogenic.R
import com.example.yxm.photogenic.utils.ScreenHelper
import com.example.yxm.photogenic.widget.CircleImageView

/**
 *Created by yxm on 2020/2/20
 *@function
 */
class CommunityRecommendAdapter : BaseQuickAdapter<CommunityBean.Issue, BaseViewHolder>(R.layout.item_community_recommend) {

    override fun convert(holder: BaseViewHolder, item: CommunityBean.Issue) {
        when (item.type) {
            "pictureFollowCard" -> initPictureItem(holder, item)
            "autoPlayFollowCard" -> initVideoItem(holder, item)
        }
    }

    /**
     * 渲染图片类型item
     */
    private fun initPictureItem(helper: BaseViewHolder, item: CommunityBean.Issue) {
        with(helper) {
            if (item.data.content.data.urls!!.size <= 1) {
                setVisible(R.id.type_label, false)
            } else {
                setVisible(R.id.type_label, true)
                setImageResource(R.id.type_label, R.drawable.abc_ic_menu_copy_mtrl_am_alpha)
            }
            displayScaleImage(getView<ImageView>(R.id.community_cover_image).layoutParams as FrameLayout.LayoutParams,
                    getView(R.id.community_cover_image), item.data.content.data.cover.feed ?: "",
                    item.data.content.data.width.toInt(), item.data.content.data.height.toInt())

            ImageLoaderManager.displayImageOverrideWidthAndHeight(getView<CircleImageView>(R.id.author_avatar),
                    item.data.header.icon,
                    ScreenHelper.dip2px(context, 10f),
                    ScreenHelper.dip2px(context, 10f))
            setText(R.id.picture_brief_introduce, item.data.content.data.description)
            setText(R.id.author_name, item.data.header.issuerName)
            setText(R.id.like_count, "${item.data.content.data.consumption.collectionCount}")
            addChildClickViewIds(R.id.like_iv)
        }
    }

    /**
     * 渲染视频类型item
     */
    private fun initVideoItem(helper: BaseViewHolder, item: CommunityBean.Issue) {
        with(helper) {
            setVisible(R.id.type_label, true)
            setImageResource(R.id.type_label, R.drawable.ic_play_circle_outline_white_48dp)
            displayScaleImage(getView<ImageView>(R.id.community_cover_image).layoutParams as FrameLayout.LayoutParams,
                    getView(R.id.community_cover_image), item.data.content.data.cover.feed ?: "",
                    item.data.content.data.width.toInt(), item.data.content.data.height.toInt())

            ImageLoaderManager.displayImageOverrideWidthAndHeight(getView<CircleImageView>(R.id.author_avatar),
                    item.data.header.icon,
                    ScreenHelper.dip2px(context, 10f),
                    ScreenHelper.dip2px(context, 10f))
            setText(R.id.picture_brief_introduce, item.data.content.data.description)
            setText(R.id.author_name, item.data.header.issuerName)
            setText(R.id.like_count, "${item.data.content.data.consumption.collectionCount}")
            addChildClickViewIds(R.id.like_iv)
        }
    }

    /**
     * 根据原始宽高缩放图片
     * @param originWidth 原始宽度
     * @param originHeight 原始高度
     */
    private fun displayScaleImage(layoutParams: FrameLayout.LayoutParams, imageView: ImageView, url: String, originWidth: Int, originHeight: Int) {
        val itemWidth = (ScreenHelper.getScreenWidth(imageView.context) - 4 * 3) / 2
        layoutParams.width = itemWidth
        val scale = (itemWidth + 0f) / originWidth
        layoutParams.height = (originHeight * scale).toInt()
        imageView.layoutParams = layoutParams
        ImageLoaderManager.displayImageOverrideWidthAndHeight(imageView, url, layoutParams.width, layoutParams.height)
    }
}