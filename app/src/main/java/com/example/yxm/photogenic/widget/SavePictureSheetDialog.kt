package com.example.yxm.photogenic.widget

import android.app.Dialog
import android.content.Context
import android.util.DisplayMetrics
import android.view.Gravity
import com.example.lib_share.R
import com.example.yxm.photogenic.utils.FileHelper
import kotlinx.android.synthetic.main.dialog_bottom_sheet.*

/**
 *Created by yxm on 2020/3/12
 *@function 通用底部dialog
 */
class SavePictureSheetDialog : Dialog {

    private val dm: DisplayMetrics
    private var mUrl: String? = null

    constructor(context: Context) : this(context, R.style.SheetDialogStyle)

    constructor(context: Context,url: String) : this(context, R.style.SheetDialogStyle){
        mUrl = url
    }

    constructor(context: Context, style: Int = R.style.SheetDialogStyle) : super(context, style) {
        setContentView(com.example.yxm.photogenic.R.layout.dialog_bottom_sheet)
        dm = context.resources.displayMetrics
        initView()
        initListener()
    }

    private fun initView() {
        /**
         * 通过获取到dialog的window来控制dialog的宽高及位置
         */
        val dialogWindow = window
        dialogWindow!!.setGravity(Gravity.BOTTOM)
        val lp = dialogWindow.attributes
        lp.width = dm.widthPixels //设置宽度
        dialogWindow.attributes = lp
        dialogWindow.setBackgroundDrawableResource(android.R.color.transparent)
    }

    private fun initListener() {
        save_picture.setOnClickListener {
            mUrl?.let {
                FileHelper.saveImage(it)
            }
            if(isShowing) {
                dismiss()
            }
        }
        cancel_save.setOnClickListener {
            if (isShowing) {
                dismiss()
            }
        }
    }
}