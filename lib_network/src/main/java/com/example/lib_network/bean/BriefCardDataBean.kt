package com.example.lib_network.bean

/**
 * Created by yxm on 2020-1-14
 * @function: BriefCard类型bean
 */
data class BriefCardDataBean(val dataType: String,
                             val id: Int,
                             val icon: String,
                             val title: String,
                             val subTitle: Any?,
                             val description: String,
                             val actionUrl: String,
                             val adTrack: Any?,
                             val follow: CommonVideoBean.ResultBean.ResultData.Author.FollowBean,
                             val ifPgc: Boolean,
                             val uid: Long,
                             val isShowNotificationIcon: Boolean,
                             val expert: Boolean
)