package com.example.yxm.photogenic.widget

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import com.example.yxm.photogenic.utils.ScreenHelper

/**
 *Created by yxm on 2020/3/3
 *@function 下滑关闭imageView
 */
class SlideDownCloseImageView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyle: Int = 0) :
        AppCompatImageView(context, attributeSet, defStyle), GestureDetector.OnGestureListener {

    var mListener: OnSlideOverListener? = null
    private val detector: GestureDetector by lazy {
        GestureDetector(context, this)
    }

    private val screenHeight = ScreenHelper.getScreenHeight(context) //设备屏幕高度
    private var oldX = 0f
    private var oldY = 0f //手机放在屏幕的坐标
    private var movY = 0f //移动中在屏幕上的坐标
    private var isFinish = false //是否执行关闭页面的操作


    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        detector.onTouchEvent(event)
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                oldX = event.rawX
                oldY = event.rawY
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                if (isFinish) {
                    isFinish = false
                    mListener?.onSlideOver()
                }
            }
            MotionEvent.ACTION_MOVE -> {
                isFinish = false
                val movX = event.rawX - oldX
                movY = event.rawY - oldY
                if (Math.abs(movX) > Math.abs(movY)) {
                    if (movX < 0) {
                        Log.e("ldd------", "左滑动")
                    } else {
                        Log.e("ldd------", "右滑动")
                    }
                } else {
                    if (movY < 0) {
                        Log.e("ldd------", "上滑动")
                    } else {
                        if (movY > screenHeight / 6) {
                            isFinish = true
                            Log.e("ldd------", "下滑动")
                        }
                    }
                }
                return false
            }
            else -> return false
        }
        return true
    }

    override fun onShowPress(e: MotionEvent?) {}

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        mListener?.onViewClick()
        return false
    }

    override fun onDown(e: MotionEvent?): Boolean {
        return false
    }

    override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
        val movX = e2.rawX - e1.rawX
        val movY = e2.rawY - e1.rawY
        if (Math.abs(movX) > Math.abs(movY)) {
            if (movX < 0) {
                Log.e("ldd------2", "左滑动")
            } else {
                Log.e("ldd------2", "右滑动")
            }
        } else {
            if (movY < 0) {
                Log.e("ldd------2", "上滑动")
            } else {
                isFinish = true
                Log.e("ldd------2", "下滑动")
            }
        }
        return true
    }

    override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
        return false
    }

    override fun onLongPress(e: MotionEvent?) {}

    fun setOnSlideDownListener(listener: OnSlideOverListener){
        mListener = listener
    }

    interface OnSlideOverListener {
        //下滑结束
        fun onSlideOver()
        //点击view,onTouchEvent级别高于onClick，所以点击view时并不会触发onClick事件，
        //这里监听单击手势，使用接口回调解决
        fun onViewClick()
    }

}