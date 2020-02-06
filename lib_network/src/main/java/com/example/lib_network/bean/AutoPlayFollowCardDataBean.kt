package com.example.lib_network.bean

/**
 * Created by yxm on 2020-1-14
 * @function:AutoPlayFollowCard类型bean
 */
data class AutoPlayFollowCardDataBean(val dataType: String, val header: CommunityRecommendBean.CommunityRecommendResultBean.RecommendDataBean.RecommendHeaderBean,
                                      val content: AutoPlayCardContent, val adTrack: Any?) {

    data class AutoPlayCardContent(val type: String, val data: AutoPlayCardContentDataBean, val tag: Any?,
                                   val id: Long, val adIndex: Int)

    data class AutoPlayCardContentDataBean(val dataType: String, val id: Long, val title: String, val description: String,
                                           val library: String, val tags: ArrayList<CommonVideoBean.ResultBean.ResultData.TagBean>,
                                           val consumption: CommonVideoBean.ResultBean.ResultData.Consumption, val resourceType: String,
                                           val uid: Long, val createTime: Long, val updateTime: Long, val collected: Boolean, val reallyCollected: Boolean,
                                           val owner: CommunityRecommendBean.CommunityRecommendResultBean.RecommendDataBean.RecommendContentBean.ContentDataBean.ContentDataOwnerBean,
                                           val cover: CommonVideoBean.ResultBean.ResultData.Cover, val selectedTime: Any?, val checkStatus: String, val area: String,
                                           val city: String, val longitude: Double, val latitude: Double, val ifMock: Boolean, val validateStatus: String, val validateResult: String,
                                           val width: Long, val height: Long, val addWaterMark: Boolean, val recentOnceReply: RecentOnceReply?, val privateMessageActionUrl: String?,
                                           val playUrl: String, val status: Int, val releaseTime: Long, val duration: Int,val transId: Any?,val type: String,val validateTaskId: String,
                                           val playUrlWatermark: String){
        data class RecentOnceReply(val dataType: String,val message: String,
                                   val nickname: String,val actionUrl: String,val contentType: Any?)
    }

}