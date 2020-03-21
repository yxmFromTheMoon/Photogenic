package com.example.yxm.photogenic.widget

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.example.yxm.photogenic.R
import com.example.yxm.photogenic.font.FontTextView
import kotlinx.android.synthetic.main.widget_footer_view.view.*

/**
 *Created by yxm on 2020/2/6
 *@function footerView
 */
class FooterView(context: Context, attributeSet: AttributeSet? = null, defStyleAttr: Int = 0)
    : RelativeLayout(context,attributeSet,defStyleAttr) {

    private var footerView:FontTextView

    init {
        View.inflate(context, R.layout.widget_footer_view,this)
        footerView = footer_view
    }

    /**
     * 设置footer的颜色
     */
    @TargetApi(Build.VERSION_CODES.M)
    fun setFooterColor(colorId: Int){
        footerView.setTextColor(resources.getColor(colorId,null))
    }
}