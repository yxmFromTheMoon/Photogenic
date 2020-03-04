package com.example.yxm.photogenic.module.picturepreview

import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.lib_imageloader.ImageLoaderManager
import com.example.yxm.photogenic.application.MyApplication
import com.example.yxm.photogenic.widget.SlideDownCloseImageView

/**
 *Created by yxm on 2020/3/2
 *@function 查看图片adapter
 */
class PicturePagerAdapter(urls: ArrayList<String>) : PagerAdapter() {

    private var mUrls: ArrayList<String> = urls
    private var mListener: OnPhotoViewClickListener? = null

    override fun isViewFromObject(p0: View, p1: Any): Boolean {
        return p0 == p1
    }

    override fun getCount(): Int {
        return mUrls.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val imageView = SlideDownCloseImageView(MyApplication.instance)
        imageView.scaleType = ImageView.ScaleType.FIT_XY

        imageView.setOnSlideDownListener(object : SlideDownCloseImageView.OnSlideOverListener {
            override fun onSlideOver() {
                mListener?.onSlideDown()
            }

            override fun onViewClick() {
                mListener?.onPhotoViewClick()
            }
        })
        ImageLoaderManager.displayImageForView(imageView, mUrls[position])
        container.addView(imageView)
        return imageView
    }

    override fun destroyItem(container: ViewGroup, position: Int, any: Any) {
        super.destroyItem(container, position, any)
        container.removeView(any as View)
    }

    fun setOnPhotoViewClickListener(listener: OnPhotoViewClickListener) {
        this.mListener = listener
    }

    interface OnPhotoViewClickListener {

        fun onPhotoViewClick()

        fun onSlideDown()
    }
}