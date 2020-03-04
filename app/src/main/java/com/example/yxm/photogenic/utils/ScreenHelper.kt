package com.example.yxm.photogenic.utils

import android.content.Context
import android.util.Log
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.example.yxm.photogenic.application.MyApplication


/**
 *Created by yxm on 2020/2/20
 *@function
 */
object ScreenHelper {

    /**
     * 获取屏幕宽度
     */
    fun getScreenWidth(context: Context): Int {
        val resources = context.resources
        val dm = resources.displayMetrics
        return dm.widthPixels
    }

    /**
     * 获取屏幕高度
     */
    fun getScreenHeight(context: Context): Int {
        val resources = context.resources
        val dm = resources.displayMetrics
        return dm.heightPixels
    }

    /**
     * dip转px
     */
    fun dip2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * px转dip
     */
    fun px2dip(context: Context, pxValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

    /**
     * 根据图片宽高获取缩放比例
     * @param width 宽
     * @param height 高
     */
    fun scaleImage(layoutParams: ViewGroup.LayoutParams, width: Long, height: Long): ViewGroup.LayoutParams {
        val screenWidth = getScreenWidth(MyApplication.instance)
        val screenHeight = getScreenHeight(MyApplication.instance)
        //图片宽度大于屏幕，但高度小于屏幕，高缩放，宽度缩小至屏幕宽
        if (width >= screenWidth && height <= screenHeight) {
            val scale = (screenWidth + 0f) / width
            layoutParams.width = screenWidth
            layoutParams.height = (height * scale).toInt()
        }
        //图片宽度小于屏幕，但高度大于屏幕，宽度缩放，高度缩小至屏高
        if (width <= screenWidth && height >= screenHeight) {
            val scale = (screenHeight + 0f) / height
            layoutParams.width = (width * scale).toInt()
            layoutParams.height = screenHeight
        }
        //图片高度和宽度都小于屏幕，宽度放大至屏幕宽，高度缩放
        if (width < screenWidth && height < screenHeight) {
            val scale = (width + 0f) / screenWidth
            layoutParams.width = screenWidth
            layoutParams.height = (height * scale).toInt()
        }
        //图片高度和宽度都大于屏幕,则对宽高进行同比例缩放
        if (width > screenWidth && height > screenHeight) {
            val widthScale = (screenWidth + 0f) / width
            val heightScale = (screenHeight + 0f) / height
            val scale = if (widthScale > heightScale) heightScale else widthScale
            layoutParams.width = (width * scale).toInt()
            layoutParams.height = (height * scale).toInt()
        }
        return layoutParams
    }
}