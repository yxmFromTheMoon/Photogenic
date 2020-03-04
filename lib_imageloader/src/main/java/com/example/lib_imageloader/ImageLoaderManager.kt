package com.example.lib_imageloader

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

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
        Glide.with(imageView.context)
                .load(resourceId)
                .into(imageView)
    }

    /**
     * * 为view加载网络图片
     * @param imageView
     * @param url
     */
    fun displayImageForView(imageView: ImageView, url: String) {
        Glide.with(imageView.context)
                .load(url)
                .apply(initCommonRequestOptions())
                .transition(DrawableTransitionOptions().crossFade())
                .into(imageView)
    }

    /**
     * 为view加载圆形图片
     *
     * @param imageView
     * @param url
     */
    fun displayImageForCircle(imageView: ImageView, url: String) {
        Glide.with(imageView.context)
                .asBitmap()
                .centerCrop()
                .load(url)
                .apply(initCommonRequestOptions())
                .transition(BitmapTransitionOptions.withCrossFade())
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
        Glide.with(viewGroup.context)
                .asBitmap()
                .load(url)
                .apply(initCommonRequestOptions())
                .transition(BitmapTransitionOptions.withCrossFade())
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
     * init Options
     * @return options
     */
    private fun initCommonRequestOptions(): RequestOptions {
        val options = RequestOptions()
        options.placeholder(R.mipmap.test)
                .error(R.mipmap.test)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        return options
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
            Glide.with(imageView).load(target).apply(RequestOptions().placeholder(placeholder)).into(imageView)
        }
    }

    /**
     * 加载指定宽高的图片
     */
    fun displayImageOverrideWidthAndHeight(imageView: ImageView, url: String, width: Int, height: Int) {
        Glide.with(imageView.context)
                .load(url)
                .apply(RequestOptions()
                        .placeholder(R.mipmap.test)
                        .error(R.mipmap.test)
                        .override(width, height))
                .transition(DrawableTransitionOptions().crossFade())
                .into(imageView)
    }
}