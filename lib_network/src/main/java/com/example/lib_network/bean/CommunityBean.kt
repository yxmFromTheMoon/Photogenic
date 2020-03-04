package com.example.lib_network.bean

import java.io.Serializable

/**
 *Created by yxm on 2020/1/6
 *@function 社区推荐bean 包含两种类型，picture和video
 */

data class CommunityBean(val itemList: ArrayList<Issue>, val count: Int,
                         val total: Int, val nextPageUrl: String?, val adExist: Boolean) : Serializable {

    data class Issue(val type: String, val data: IssueData,
                     val tag: Any?, val id: Long, val adIndex: Int) : Serializable {

        data class IssueData(val dataType: String, val header: IssueHeader,
                             val content: IssueContent, val adTrack: Any?) : Serializable {

            data class IssueHeader(val id: Long, val actionUrl: String, val labelList: ArrayList<String>?,
                                   val icon: String, val iconType: String, val time: Long,
                                   val showHateVideo: Boolean, val followType: String, val tagId: Int,
                                   val tagName: Any?, val issuerName: String, val topShow: Boolean) : Serializable

            data class IssueContent(val type: String, val data: ContentDataBean, val tag: Any?,
                                    val id: Long, val adIndex: Int) : Serializable {

                data class ContentDataBean(val dataType: String, val id: Long, val title: String,
                                           val description: String, val library: String, val tags: ArrayList<CommonVideoBean.ResultBean.ResultData.TagBean>,
                                           val consumption: CommonVideoBean.ResultBean.ResultData.Consumption, val resourceType: String,
                                           val uid: Long, val createTime: Long, val updateTime: Long, val collected: Boolean,
                                           val reallyCollected: Boolean, val owner: ContentDataOwnerBean, val cover: CommonVideoBean.ResultBean.ResultData.Cover,
                                           val selectedTime: Any?, val checkStatus: String, val area: String, val city: String, val longitude: Double, val latitude: Double,
                                           val ifMock: Boolean, val validateStatus: String, val validateResult: String, val width: Long, val height: Long, val addWaterMark: Boolean,
                                           val recentOnceReply: RecentOnceReply?, val privateMessageActionUrl: String?, val url: String?, val urls: ArrayList<String>?,
                                           val status: Any, val releaseTime: Long, val urlsWithWatermark: ArrayList<String>?, val duration: Int,val playUrl: String?,
                                           val transId: Any?, val type: String?, val validateTaskId: String?, val playUrlWatermark: String?) : Serializable {

                    data class RecentOnceReply(val dataType: String, val message: String,
                                               val nickname: String, val actionUrl: String, val contentType: Any?) : Serializable

                    data class ContentDataOwnerBean(val uid: Long, val nickname: String, val avatar: String, val userType: String,
                                                    val ifPgc: Boolean, val description: String, val area: String?, val gender: String,
                                                    val registDate: Long, val releaseDate: Long, val cover: String?, val actionUrl: String,
                                                    val followed: Boolean, val limitVideoOpen: Boolean, val library: String, val birthday: Long,
                                                    val country: String, val city: String, val university: String?, val job: String, val expert: Boolean) : Serializable
                }
            }
        }
    }
}
