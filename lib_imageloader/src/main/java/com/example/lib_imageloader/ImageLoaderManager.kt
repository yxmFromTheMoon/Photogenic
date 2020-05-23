package com.example.lib_imageloader

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Looper
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.DrawableRes
import com.bumptech.glide.load.engine.cache.ExternalPreferredCacheDiskCacheFactory
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import java.io.File

/**
 *Created by yxm on 2020/1/3
 *@function 图片加载类
 */
object ImageLoaderManager {

    /**
     * 采样时默认需要的图片宽高
     */
    private const val DEFAULT_SAMPLE_WIDTH = 320
    private const val DEFAULT_SAMPLE_HEIGHT = 320

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
     * 为viewGroup加载背景并模糊处理
     *
     * @param viewGroup
     * @param url
     */
    fun displayImageForViewGroup(viewGroup: ViewGroup, url: String) {
        var dispose: Disposable? = null
        GlideApp.with(viewGroup.context)
                .asBitmap()
                .load(url)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        dispose = Observable.just(resource)
                                .map {
                                    //为bitmap作模糊处理
                                    BitmapDrawable(null, Utils.doBlur(resource, 100, true))
                                }.subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe { drawable ->
                                    viewGroup.background = drawable
                                }
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                        dispose?.dispose()
                    }
                })
    }

    /**
     * 自定义placeholder加载资源图片
     */
    fun displayImageWithPlaceholder(resId: Int, imageView: ImageView, @DrawableRes placeholder: Int) {
        GlideApp.with(imageView)
                .load(resId)
                .apply(RequestOptions()
                        .placeholder(placeholder)
                        .error(placeholder))
                .into(imageView)
    }

    /**
     * 自定义placeholder加载网络图片
     */
    fun displayImageWithPlaceholder(url: String, imageView: ImageView, @DrawableRes placeholder: Int) {
        GlideApp.with(imageView)
                .load(url)
                .apply(RequestOptions()
                        .placeholder(placeholder)
                        .error(placeholder))
                .into(imageView)
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
     * 加载大图
     *
     */
    fun displayBigPicture(imageView: ImageView, url: String) {
        val observable1 = Observable.create<Bitmap> {
            val bitmap = Utils.getFitBitmap(url, DEFAULT_SAMPLE_WIDTH, DEFAULT_SAMPLE_HEIGHT)
            it.onNext(bitmap)
            it.onComplete()
        }.subscribeOn(Schedulers.io())

        val observable2 = Observable.create<ViewGroup.LayoutParams> {
            val sizeInfo = Utils.getImageSizeAhead(url)
            val lp = scaleImage(imageView.context, imageView.layoutParams, sizeInfo[0].toLong(), sizeInfo[1].toLong())
            it.onNext(lp)
            it.onComplete()
        }.subscribeOn(Schedulers.io())

        val observableZip = Observable.zip(observable1, observable2,
                BiFunction<Bitmap, ViewGroup.LayoutParams, BitmapWithLayoutParams> { bitmap, height ->
                    return@BiFunction BitmapWithLayoutParams(bitmap, height)
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    imageView.layoutParams = it.layoutParams
                    GlideApp.with(imageView.context)
                            .load(it.bitmap)
                            .into(imageView)
                }, {
                    Toast.makeText(imageView.context, "图片加载失败,请重试", Toast.LENGTH_SHORT).show()
                })
    }

    /**
     * 根据图片宽高获取缩放比例
     * @param width 宽
     * @param height 高
     */
    private fun scaleImage(context: Context, layoutParams: ViewGroup.LayoutParams, width: Long, height: Long): ViewGroup.LayoutParams {
        val screenWidth = getScreenWidth(context)
        val screenHeight = getScreenHeight(context)
        //图片宽度大于屏幕，但高度小于屏幕，高缩放，宽度缩小至屏幕宽
        if (width >= screenWidth && height <= screenHeight) {
            val scale = (screenWidth + 0f) / width
            layoutParams.width = screenWidth
            layoutParams.height = (height * scale).toInt()
//            Log.d("Picture","缩放类型:1,屏幕宽:$screenWidth,屏幕高:$screenHeight,原始图片宽:$width,原始图片高" +
//                    "$height,缩放比例:$scale")
        }
        //图片宽度小于屏幕，但高度大于屏幕，宽度缩放，高度缩小至屏高
        if (width <= screenWidth && height >= screenHeight) {
            val scale = (screenHeight + 0f) / height
            layoutParams.width = (width * scale).toInt()
            layoutParams.height = screenHeight
//            Log.d("Picture","缩放类型:2,屏幕宽:$screenWidth,屏幕高:$screenHeight,原始图片宽:$width,原始图片高" +
//                    "$height,缩放比例:$scale")
        }
        //图片高度和宽度都小于屏幕，宽度放大至屏幕宽，高度缩放
        if (width < screenWidth && height < screenHeight) {
            val scale = (screenWidth + 0f) / width
            layoutParams.width = screenWidth
            layoutParams.height = (height * scale).toInt()
//            Log.d("Picture","缩放类型:3,屏幕宽:$screenWidth,屏幕高:$screenHeight,原始图片宽:$width,原始图片高" +
//                    "$height,缩放比例:$scale")
        }
        //图片高度和宽度都大于屏幕,则对宽高进行同比例缩放
        if (width > screenWidth && height > screenHeight) {
            val widthScale = (screenWidth + 0f) / width
            val heightScale = (screenHeight + 0f) / height
            val scale = if (widthScale > heightScale) heightScale else widthScale
            layoutParams.width = (width * scale).toInt()
            layoutParams.height = (height * scale).toInt()
//            Log.d("Picture","缩放类型:4,屏幕宽:$screenWidth,屏幕高:$screenHeight,原始图片宽:$width,原始图片高" +
//                    "$height,缩放比例:$scale")
        }
        return layoutParams
    }

    /**
     * 获取屏幕宽度
     */
    private fun getScreenWidth(context: Context): Int {
        val resources = context.resources
        val dm = resources.displayMetrics
        return dm.widthPixels
    }

    /**
     * 获取屏幕高度
     */
    private fun getScreenHeight(context: Context): Int {
        val resources = context.resources
        val dm = resources.displayMetrics
        return dm.heightPixels
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