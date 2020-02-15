package com.example.yxm.photogenic.widget

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.example.yxm.photogenic.R
import com.example.yxm.photogenic.font.FontTextView
import kotlinx.android.synthetic.main.widget_left_back.view.*

/**
 *Created by yxm on 2020/2/14
 *@function 二级页title
 */
class LeftBackTitle @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyle: Int = 0) :
        LinearLayout(context, attributeSet, defStyle){

    private var mTextView: FontTextView
    private var centerTitle: String?
    private var mBackView: ImageView

    init {
        View.inflate(getContext(), R.layout.widget_left_back, this)
        val typedArray = getContext().obtainStyledAttributes(attributeSet, R.styleable.LeftBackTitle)
        centerTitle = typedArray.getString(R.styleable.LeftBackTitle_centerTitle)
        mTextView = center_title
        mBackView = left_back
        centerTitle?.let {
            mTextView.text = it
        }
        typedArray.recycle()
    }

    fun setLeftFinish(context: Activity){
        mBackView.setOnClickListener {
            context.finish()
        }
    }
}