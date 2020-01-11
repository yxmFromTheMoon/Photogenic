package com.example.yxm.photogenic.font

import android.content.Context
import android.graphics.Typeface
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet
import com.example.yxm.photogenic.R

/**
 * Created by yxm on 2020-1-11
 * @function: 自定义字体
 */
class FontTextView
    @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyle: Int = 0):
        AppCompatTextView(context,attributeSet,defStyle){
    private var type: String?

    init {
        val typedArray = getContext().obtainStyledAttributes(attributeSet,R.styleable.FontTextView)
        type = typedArray.getString(R.styleable.FontTextView_fontType)
        type?.let {
            setTypeface(createTypeface(getContext(),"font/${type}.ttf"))
        }
        typedArray.recycle()
    }

    private fun createTypeface(context:Context,fontType: String): Typeface{
        return Typeface.createFromAsset(context.assets,fontType)
    }

}