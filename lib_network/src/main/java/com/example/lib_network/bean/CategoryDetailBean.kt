package com.example.lib_network.bean

import java.io.Serializable

/**
 * Created by yxm on 2020-1-14
 * @function: 分类videoBean，广告、创意等等
 */
data class CategoryDetailBean(val code: Int, val message: String, val result: ArrayList<FollowCardBean>) {

    data class FollowCardBean(val data: FollowCardDataBean, val adIndex: Int, val tag: Any?, val id: Long, val type: String) : Serializable {

        data class FollowCardDataBean(val dataType: String, val header: FollowCardHeaderBean,
                                      val content: FollowCardContentBean, val adTrack: Any?) : Serializable {

            data class FollowCardHeaderBean(val id: Long, val title: String, val font: Any?, val subTitle: Any?,
                                            val subTitleFont: Any?, val textAlign: String, val cover: Any?,
                                            val label: Any?, val actionUrl: String, val labelList: Any?,
                                            val icon: String, val iconType: String, val description: String,
                                            val time: Long, val showHateVideo: Boolean, val rightText: Any?) : Serializable

            data class FollowCardContentBean(val data: CommonVideoBean.ResultBean.ResultData, val adIndex: Int, val tag: Any?, val id: Long, val type: String) : Serializable
        }
    }
}