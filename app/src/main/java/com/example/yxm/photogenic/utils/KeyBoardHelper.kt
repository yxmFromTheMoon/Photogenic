package com.example.yxm.photogenic.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.example.yxm.photogenic.application.MyApplication
import java.util.*

/**
 *Created by yxm on 2020/2/5
 *@function 软键盘帮助类
 */
object KeyBoardHelper {

    /**
     * 隐藏软件盘
     */
    fun hideKeyBoard(view: View) {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (imm.isActive) {
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    /**
     * 弹出软键盘
     */
    fun openKeyBoard(editText: EditText) {
        editText.isFocusable = true
        editText.isFocusableInTouchMode = true
        editText.requestFocus()
        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                val imm = MyApplication.instance.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(editText, 0)
                editText.setSelection(editText.text.length)
            }
        }, 200)
    }
}