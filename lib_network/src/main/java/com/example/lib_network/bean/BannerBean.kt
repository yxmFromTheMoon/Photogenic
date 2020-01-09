package com.example.lib_network.bean

/**
 * Created by yxm on 2020-1-7
 * @function:轮播图实体类
 */

data class BannerDataBean(val dataType: String, val itemList: ArrayList<BannerItemBean>, val count: Int) {

    data class BannerItemBean(val type: String, val data: SpecificBannerBean, val tag: Any?, val id: Int, val adIndex: Int) {

        data class SpecificBannerBean(val dataType: String, val id: Int, val title: String,
                                      val description: String, val image: String, val actionUrl: String,
                                      val adTrack: Any?, val shade: Boolean, val label: BannerLabel,
                                      val labelList: Any?, val header: BannerHeader, val autoPlay: Boolean) {

            data class BannerLabel(val text: String, val card: String, val detail: Any?)

            data class BannerHeader(val id: Int, val title: String?, val font: Any?,
                                    val subTitle: Any?, val subTitleFont: Any?, val textAlign: String,
                                    val cover: Any?, val label: Any?, val actionUrl: String?,
                                    val labelList: Any?, val rightText: Any?, val icon: Any?, val description: Any?)
        }
    }
}
