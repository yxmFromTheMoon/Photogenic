package com.example.lib_network.bean

/**
 *Created by yxm on 2020/1/6
 *@function 社区推荐bean
 */
data class CommunityRecommendBean(val itemList: ArrayList<CommunityRecommendResultBean>, val count: Int,
                                  val total: Int, val nextPageUrl: String, val adExist: Boolean) {

    data class CommunityRecommendResultBean(val type: String, val data: RecommendDataBean,
                                            val tag: String?, val id: Long, val adIndex: Int) {
        data class RecommendDataBean(val dataType: String, val header: RecommendHeaderBean,
                                     val content: RecommendContentBean, val adTrack: String?) {

            data class RecommendHeaderBean(val id: Long, val actionUrl: String, val labelList: ArrayList<String>?,
                                           val icon: String, val iconType: String, val time: Long,
                                           val showHateVideo: Boolean, val followType: String, val tagId: Int,
                                           val tagName: String?, val issuerName: String, val topShow: Boolean)

            data class RecommendContentBean(val type: String, val data: ContentDataBean, val tag: String?,
                                            val id: Long, val adIndex: Int) {

                data class ContentDataBean(val dataType: String, val id: Long, val title: String,
                                           val description: String, val library: String, val tags: ArrayList<CommonVideoBean.ResultBean.ResultData.TagBean>,
                                           val consumption: CommonVideoBean.ResultBean.ResultData.Consumption, val resourceType: String,
                                           val uid: Long, val createTime: Long, val updateTime: Long, val collected: Boolean,
                                           val reallyCollected: Boolean, val owner: ContentDataOwnerBean, val cover: CommonVideoBean.ResultBean.ResultData.Cover,
                                           val selectedTime: Long?, val checkStatus: String, val area: String, val city: String, val longitude: Double, val latitude: Double,
                                           val ifMock: Boolean, val validateStatus: String, val validateResult: String, val width: Long, val height: Long, val addWaterMark: Boolean,
                                           val recentOnceReply: String?, val privateMessageActionUrl: String?, val url: String, val urls: ArrayList<String>,
                                           val status: Int, val releaseTime: Long, val urlsWithWatermark: ArrayList<String>) {

                    data class ContentDataOwnerBean(val uid: Long, val nickname: String, val avatar: String, val userType: String,
                                                    val ifPgc: Boolean, val description: String, val area: String?, val gender: String,
                                                    val regisDate: Long, val releaseDate: Long, val cover: String, val actionUrl: String,
                                                    val followed: Boolean, val limitVideoOpen: Boolean, val library: String, val birthday: Long,
                                                    val country: String, val city: String, val university: String?, val job: String, val expert: Boolean)
                }
            }
        }
    }
}