package com.example.yxm.photogenic.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.example.yxm.photogenic.R
import com.example.yxm.photogenic.font.FontTextView
import kotlinx.android.synthetic.main.widget_right_arrow_select.view.*

/**
 *Created by yxm on 2020/2/8
 *@function 带向右箭头的view
 */
class RightArrowSelectView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyle: Int = 0) :
        LinearLayout(context, attributeSet, defStyle) {

    private var mTextView: FontTextView
    private var rightText: String?

    init {
        View.inflate(getContext(), R.layout.widget_right_arrow_select, this)
        val typedArray = getContext().obtainStyledAttributes(attributeSet, R.styleable.RightArrowSelectView)
        rightText = typedArray.getString(R.styleable.RightArrowSelectView_rightText)
        mTextView = right_arrow_tv
        rightText?.let {
            mTextView.text = it
        }
        typedArray.recycle()
    }
}