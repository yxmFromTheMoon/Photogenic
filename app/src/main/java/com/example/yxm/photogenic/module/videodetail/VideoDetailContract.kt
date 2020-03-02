package com.example.yxm.photogenic.module.videodetail

import com.example.lib_network.bean.CommonVideoBean
import com.example.yxm.photogenic.base.BaseView
import com.example.yxm.photogenic.base.IPresenter

/**
 *Created by yxm on 2020/2/25
 *@function 视频详情contract
 */
interface VideoDetailContract {

    interface IVideoDetailView : BaseView {
        //设置视频相关信息
        fun setVideoInfo(video: Any, fromWhere: Int, type: String)

        fun setRelativeVideo(data: ArrayList<CommonVideoBean.ResultBean>)
        //播放视频
        fun playVideo(url: String, title: String)

    }

    interface IVideoDetailPresenter : IPresenter<IVideoDetailView> {
        //加载相关视频
        fun loadRelativeVideo(id: Long)

        //加载视频信息
        fun loadVideoInfo(video: Any, fromWhere: Int)
    }
}