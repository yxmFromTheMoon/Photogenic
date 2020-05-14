package com.example.yxm.photogenic.module.discovery

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.lib_imageloader.ImageLoaderManager
import com.youth.banner.adapter.BannerAdapter

/**
 * @author yxm
 * 2020/5/13 16:23
 * @function bannerAdapter
 */
class BannerImageAdapter(urls: List<String>) : BannerAdapter<String, BannerImageAdapter.BannerViewHolder>(urls) {

    override fun onCreateHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val imageView = ImageView(parent.context)
        imageView.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT)
        imageView.scaleType = ImageView.ScaleType.FIT_XY
        return BannerViewHolder(imageView)
    }

    override fun onBindView(holder: BannerViewHolder, data: String, position: Int, size: Int) {
        ImageLoaderManager.displayImageForView(holder.itemView as ImageView, data)
    }

    class BannerViewHolder(itemView: ImageView) : RecyclerView.ViewHolder(itemView) {

    }
}