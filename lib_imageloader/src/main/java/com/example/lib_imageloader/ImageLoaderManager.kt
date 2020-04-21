package com.example.lib_imageloader

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Looper
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory
import com.bumptech.glide.load.engine.cache.ExternalPreferredCacheDiskCacheFactory
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.lang.Exception

/**
 *Created by yxm on 2020/1/3
 *@function 图片加载类
 */
object ImageLoaderManager {


    /**
     * * 为view加载资源图片
     * @param imageView
     * @param resourceId
     */
    fun displayImageForView(imageView: ImageView, resourceId: Int) {
        GlideApp.with(imageView.context)
                .load(resourceId)
                .into(imageView)
    }

    /**
     * * 为view加载网络图片
     * @param imageView
     * @param url
     */
    fun displayImageForView(imageView: ImageView, url: String) {
        GlideApp.with(imageView.context)
                .load(url)
                .into(imageView)
    }

    /**
     * 为view加载圆形图片
     *
     * @param imageView
     * @param url
     */
    fun displayImageForCircle(imageView: ImageView, url: String) {
        GlideApp.with(imageView.context)
                .asBitmap()
                .centerCrop()
                .load(url)
                .into(object : BitmapImageViewTarget(imageView) {
                    override fun setResource(resource: Bitmap?) {
                        val drawable = RoundedBitmapDrawableFactory.create(imageView.resources, resource)
                        drawable.isCircular = true
                        imageView.setImageDrawable(drawable)
                    }
                })
    }

    /**
     * 为viewGroup加载背景并模糊处理
     *
     * @param viewGroup
     * @param url
     */
    fun displayImageForViewGroup(viewGroup: ViewGroup, url: String) {
        GlideApp.with(viewGroup.context)
                .asBitmap()
                .load(url)
                .into(object : SimpleTarget<Bitmap>() {
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        val disposable = Observable.just(resource)
                                .map {
                                    //为bitmap作模糊处理
                                    BitmapDrawable(Utils.doBlur(resource, 100, true))
                                }.subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe { drawable ->
                                    viewGroup.background = drawable
                                }
                    }
                })
    }

    /**
     * 自定义placeholder加载
     */
    fun displayImageWithPlaceholder(any: Any, imageView: ImageView?, @DrawableRes placeholder: Int) {
        var target = any
        imageView?.let {
            if (!Utils.isNetworkConnected(imageView.context)) {
                target = ContextCompat.getDrawable(imageView.context, placeholder) as Drawable
            }
            GlideApp.with(imageView)
                    .load(target)
                    .apply(RequestOptions()
                            .placeholder(placeholder)
                            .error(placeholder))
                    .into(imageView)
        }
    }

    /**
     * 加载指定宽高的图片
     */
    fun displayImageOverrideWidthAndHeight(imageView: ImageView, url: String, width: Int, height: Int) {
        GlideApp.with(imageView.context)
                .load(url)
                .apply(RequestOptions()
                        .override(width, height))
                .into(imageView)
    }

    /**
     * 清除磁盘缓存
     * 耗时操作，在子线程中进行
     */
    private fun clearDiskCache(context: Context) {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                Thread(Runnable {
                    GlideApp.get(context).clearDiskCache()
                }).start()
            } else {
                GlideApp.get(context).clearDiskCache()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 清除内存缓存
     * 主线程中进行
     */
    private fun clearMemoryCache(context: Context) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            GlideApp.get(context).clearMemory()
        }
    }

    /**
     * 删除文件夹下的缓存文件
     */
    private fun deleteFolderFile(path: String?, isDelete: Boolean) {
        if (!path.isNullOrEmpty()) {
            try {
                val file = File(path)
                if (file.isDirectory) {
                    file.listFiles().forEach {
                        deleteFolderFile(it.absolutePath, true)
                    }
                }
                if (isDelete) {
                    if (!file.isDirectory) {
                        file.delete()
                    } else {
                        if (file.listFiles().isEmpty()) {
                            file.delete()
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /**
     * 清除所有缓存
     */
    fun clearAllImageCache(context: Context) {
        clearMemoryCache(context)
        clearDiskCache(context)
        val imageCacheDir = "${context.externalCacheDir}${ExternalPreferredCacheDiskCacheFactory.DEFAULT_DISK_CACHE_DIR}"
        deleteFolderFile(imageCacheDir, true)
    }
}