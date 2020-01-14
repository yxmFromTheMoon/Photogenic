package com.example.lib_network.bean

/**
 * Created by yxm on 2020-1-14
 * @function: FollowCard类型
 */
class FollowCardDataBean(val dataType: String, val header: FollowCardHeaderBean,
                         val content: CommonVideoBean.ResultBean.ResultData, val adTrack: Any?) {

    data class FollowCardHeaderBean(val id: Long, val title: String, val font: Any?, val subTitle: Any?,
                                    val subTitleFont: Any?, val textAlign: String, val cover: Any?,
                                    val label: Any?, val actionUrl: String, val labelList: ArrayList<String>?,
                                    val icon: String, val iconType: String, val description: String,
                                    val time: Long, val showHateVideo: Boolean)
}