package com.example.lib_network.bean

/**
 * Created by yxm on 2020-1-6
 * @function: 社区关注bean
 * 总体结构和CommonVideoBean类似，多了个header
 */
data class CommunityFollowBean(val itemList: ArrayList<CommunityFollowResultBean>,val count: Int,
                               val total: Int,val nextPageUrl: String?,val adExist: Boolean,
                               val updateTime: Long?,val refreshCount: Int,val lastStartId: Int) {

    data class CommunityFollowResultBean(val type: String,val data: CommunityFollowResultDataBean,val tag: String?,
                                         val id: Int,val adIndex: Int){

        data class CommunityFollowResultDataBean(val dataType: String,val header: DataHeaderBean,
                                                 val itemList: ArrayList<CommonVideoBean>,val count: Int,
                                                 val adTrack: String?,val footer: String?){

            data class DataHeaderBean(val id: Int,val icon: String,val iconType:String,
                                      val title: String,val subTitle:String?,val description: String,
                                      val actionUrl: String,val adTrack:String?,val follow: CommonVideoBean.ResultBean.ResultData.Author.FollowBean,
                                      val ifPgc: Boolean,val uid: Int,val ifShowNotificationIcon: Boolean,val expert: Boolean)

        }
    }
}