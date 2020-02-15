package com.example.yxm.photogenic.utils

import android.content.Context
import android.widget.ImageView
import com.example.lib_imageloader.ImageLoaderManager
import com.youth.banner.loader.ImageLoader

/**
 *Created by yxm on 2020/2/8
 *@function banner加载器
 */
class GlideImageLoader: ImageLoader() {

    override fun displayImage(context: Context, path: Any, imageView: ImageView) {
        ImageLoaderManager.displayImageForView(imageView,path as String)
    }
}