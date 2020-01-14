package com.example.lib_network.bean

/**
 * Created by yxm on 2020-1-14
 * @function: textCard类型bean
 */
data class TextCardDataBean(
        val dataType: String,
        val id: Int,
        val type: String,
        val text: String,
        val subTitle: Any?,
        val actionUrl: Any?,
        val adTrack: Any?,
        val follow: Any?
)