package com.example.lib_network.bean

import com.chad.library.adapter.base.entity.MultiItemEntity
import java.io.Serializable

/**
 *Created by yxm on 2020/2/15
 *@function 首页推荐接口bean
 */
data class HomeBean(val itemList: ArrayList<Issue>, val count: Int,
                    val total: Int, val nextPageUrl: String?, val adExist: Boolean){

    data class Issue(val type: String, val data: Data, val tag: String?, val id: Int, val adIndex: Int): MultiItemEntity, Serializable{

        override val itemType: Int
            get() = when(type){
                "squareCardCollection" -> SQUARE_CARD
                "textCard" -> TEXT_CARD
                "followCard" -> FOLLOW_CARD
                "videoSmallCard" -> VIDEO_CARD
                else -> 0
            }

        companion object{
            const val SQUARE_CARD = 1
            const val TEXT_CARD = 2
            const val FOLLOW_CARD = 3
            const val VIDEO_CARD = 4
        }

        data class Data(val dataType: String, val header: CategoryDetailBean.FollowCardBean.FollowCardDataBean.FollowCardHeaderBean?,
                        val itemList: ArrayList<CategoryDetailBean.FollowCardBean>?,
                        val count: Int?, val adTrack: Any?, val footer: Any?, val id: Int?,
                        val title: String?, val description: String?, val image: String?,
                        val actionUrl: String?, val shade: Boolean?, val label: Label?,
                        val labelList: Any?, val autoPlay: Boolean?, val type: String?, val text: String?,
                        val subTitle: Any?, val follow: Any?, val content: CommonVideoBean.ResultBean?,
                        val library: String?, val tags: ArrayList<CommonVideoBean.ResultBean.ResultData.TagBean>?,
                        val consumption: CommonVideoBean.ResultBean.ResultData.Consumption?,
                        val resourceType: String?, val slogan: String?, val provider: CommonVideoBean.ResultBean.ResultData.Provider?,
                        val category: String?, val author: CommonVideoBean.ResultBean.ResultData.Author?, val cover: CommonVideoBean.ResultBean.ResultData.Cover?,
                        val playUrl: String?, val thumbPlayUrl: String?, val duration: Long?, val webUrl: CommonVideoBean.ResultBean.ResultData.WebUrl?,
                        val releaseTime: Long?, val playInfo: ArrayList<CommonVideoBean.ResultBean.ResultData.PlayInfoBean>?,
                        val campaign: Any?, val waterMarks: Any?, val ad: Boolean?, val titlePgc: Any?, val descriptionPgc: Any?, val remark: String?,
                        val ifLimitVideo: Boolean?, val searchWeight: Int?, val brandWebsiteInfo: String?, val idx: Int?,
                        val shareAdTrack: Any?, val favoriteAdTrack: Any?, val webAdTrack: Any?, val date: Long?,
                        val promotion: Any?, val descriptionEditor: Any?, val collected: Boolean?,
                        val reallyCollected: Boolean?, val played: Boolean?, val subtitles: Any?,
                        val lastViewTime: Any?, val playlists: Any?, val src: String?): Serializable {

            data class Label(val text: String?, val card: String?, val detail: Any?): Serializable

        }
    }
}