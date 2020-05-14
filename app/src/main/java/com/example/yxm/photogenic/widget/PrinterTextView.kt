package com.example.yxm.photogenic.widget

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import java.util.*

/**
 *Created by yxm on 2020/2/26
 *@function 仿打印机打字效果textView
 */
class PrinterTextView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyle: Int = 0) :
        AppCompatTextView(context, attributeSet, defStyle) {

    private var mTimer: Timer? = null
    private var mPrintStr: String = ""
    private var intervalTime = DEFAULT_TIME_DELAY
    private var intervalChar = DEFAULT_INTERVAL_CHAR
    private var printProgress = 0


    /**
     * 设置text并且打印
     */
    fun setTextAndPrint(string: String){
        setPrintText(string, DEFAULT_TIME_DELAY)
        startPrint()
    }

    /**
     * 设置打印间隔
     */
    fun setInterval(time: Int){
        setPrintText(mPrintStr,time)
    }

    /**
     * 设置打印的文字和打字间隔
     */
    private fun setPrintText(string: String, time: Int) {
        setPrintText(string,time,intervalChar)
    }

    /**
     * 设置要打字的文字，打印间隔，间隔符号
     */
    private fun setPrintText(string: String, time: Int, intervalChar: String) {
        if (string.isEmpty() && time == 0 && intervalChar.isEmpty()) {
            return
        }
        this.mPrintStr = string
        this.intervalTime = time
        this.intervalChar = intervalChar
    }

    /**
     * 开始打字
     */
    private fun startPrint() {
        if (mPrintStr.isEmpty()) {
            if (text.toString().isNotEmpty()) {
                this.mPrintStr = text.toString()
            } else {
                return
            }
        }
        text = ""
        stopPrint()
        printProgress = 0
        mTimer = Timer()
        mTimer!!.schedule(PrinterTimerTask(),intervalTime.toLong(),intervalTime.toLong())
    }

    /**
     * 停止打字
     */
    fun stopPrint(){
        mTimer?.cancel()
        mTimer = null
    }

    /**
     * 打字计时器任务
     */
    inner class PrinterTimerTask : TimerTask() {

        override fun run() {
            post{
                //如果没有显示完，则继续显示
                if(printProgress < mPrintStr.length){
                    printProgress++
                    text = "${mPrintStr.substring(0,printProgress)}${if (printProgress%2 != 0) intervalChar else ""}"
                }else{
                    text = mPrintStr
                    stopPrint()
                }
            }
        }
    }

    companion object {
        private const val TAG = "PrinterTextView"
        /**
         * 默认打字字符
         */
        private var DEFAULT_INTERVAL_CHAR = ""
        /**
         * 默认打字间隔
         */
        private var DEFAULT_TIME_DELAY = 10
    }
}