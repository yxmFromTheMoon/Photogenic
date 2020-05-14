package com.example.yxm.photogenic.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView

/**
 *Created by yxm on 2020/3/20
 *@function 不用nestedScrollView的recyclerview
 */
class CustomRecyclerView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyle: Int = 0) :
        RecyclerView(context,attributeSet,defStyle) {

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        parent.requestDisallowInterceptTouchEvent(true)
        return super.dispatchTouchEvent(ev)
    }
}