package com.example.yxm.photogenic.utils

import android.content.Context


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
}