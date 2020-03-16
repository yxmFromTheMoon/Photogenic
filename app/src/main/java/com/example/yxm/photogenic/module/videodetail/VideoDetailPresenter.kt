package com.example.yxm.photogenic.module.videodetail

import com.example.lib_network.bean.CommonVideoBean
import com.example.lib_network.bean.CommunityBean
import com.example.lib_network.bean.HomeBean
import com.example.yxm.photogenic.base.BasePresenter
import com.example.yxm.photogenic.model.VideoTypeModel
import com.example.yxm.photogenic.ui.activity.CategoryDetailActivity.Companion.CATEGORY_DETAIL
import com.example.yxm.photogenic.ui.activity.TagDetailActivity.Companion.TAG_DETAIL
import com.example.yxm.photogenic.ui.activity.VideoPlayActivity.Companion.VIDEO_DETAIL
import com.example.yxm.photogenic.ui.fragment.CommunityFollowFragment.Companion.COMMUNITY_FOLLOW
import com.example.yxm.photogenic.ui.fragment.CommunityRecommendFragment.Companion.COMMUNITY_RECOMMEND
import com.example.yxm.photogenic.ui.fragment.DiscoveryFragment.Companion.DISCOVERY
import com.example.yxm.photogenic.ui.fragment.HomePageDailyReportFragment.Companion.HOME_REPORT
import com.example.yxm.photogenic.ui.fragment.HomePageRecommendFragment.Companion.HOME_RECOMMEND
import com.example.yxm.photogenic.ui.fragment.RankFragment.Companion.RANK_FRAGMENT
import com.example.yxm.photogenic.ui.fragment.SearchResultFragment.Companion.SEARCH_RESULT

/**
 *Created by yxm on 2020/2/25
 *@function 视频详情
 */
class VideoDetailPresenter : BasePresenter<VideoDetailContract.IVideoDetailView>(), VideoDetailContract.IVideoDetailPresenter {

    private val mModel: VideoTypeModel by lazy {
        VideoTypeModel()
    }

    override fun loadRelativeVideo(id: Long) {
        checkViewAttached()
        mRootView?.showLoading()
        addSubscribe(dispose = mModel.getRelatedVideoData(id)
                .subscribe({
                    val itemList = it.itemList
                    itemList.filter { issue ->
                        issue.type == "textCard"
                    }.forEach { item ->
                        itemList.remove(item)
                    }
                    mRootView?.apply {
                        dismissLoading()
                        setRelativeVideo(itemList)
                    }
                }, {
                    mRootView?.apply {
                        dismissLoading()
                        showError("获取相关视频失败")
                    }
                }))
    }

    /**
     * 加载视频相关信息
     * @param fromWhere 从哪个页面传过来
     * @param video 视频实体类
     */
    override fun loadVideoInfo(video: Any, fromWhere: Int) {

        checkViewAttached()
        when (fromWhere) {
            //社区关注，视频播放页,排行,搜索结果
            COMMUNITY_FOLLOW, VIDEO_DETAIL,
            RANK_FRAGMENT, SEARCH_RESULT -> {
                val bean = video as CommonVideoBean.ResultBean
                getPlayInfo(bean.data, fromWhere, "")
            }
            //发现页,目录详情
            DISCOVERY, CATEGORY_DETAIL, TAG_DETAIL -> {
                val bean = video as CommonVideoBean.ResultBean.ResultData
                getPlayInfo(bean, fromWhere, "")
            }
            //首页推荐,首页日报
            HOME_RECOMMEND, HOME_REPORT -> {
                val bean = video as HomeBean.Issue
                getPlayInfo(bean, fromWhere)
            }
            //社区推荐
            COMMUNITY_RECOMMEND -> {
                val item = video as CommunityBean.Issue
                mRootView?.apply {
                    playVideo(item.data.content.data.playUrl
                            ?: "", item.data.content.data.description)
                    setVideoInfo(item, fromWhere, "")
                }
            }
        }
    }

    /**
     * 获取视频播放信息，高清，标清，线路
     */
    private fun getPlayInfo(bean: CommonVideoBean.ResultBean.ResultData, fromWhere: Int, type: String) {
        val playInfo = bean.playInfo
        if (playInfo.isNotEmpty()) {
            for (item in playInfo) {
                if (item.type == "high") {
                    val playUrl = item.url
                    mRootView?.playVideo(playUrl, bean.title)
                    break
                }
            }
            mRootView?.showError("本视频需要消耗${(playInfo[0].urlList[0].size / 1024 / 1024).toFloat()}M流量")
        } else {
            val playUrl = bean.playUrl
            mRootView?.playVideo(playUrl, bean.title)
        }
        mRootView?.setVideoInfo(bean, fromWhere, type)
    }

    /**
     * 获取视频播放信息，高清，标清，线路
     */
    private fun getPlayInfo(bean: HomeBean.Issue, fromWhere: Int) {
        if (bean.type == "followCard") {
            getPlayInfo(bean.data.content?.data!!, fromWhere, bean.type)
        } else {
            val playInfo = bean.data.playInfo
            playInfo?.let {
                if (it.isNotEmpty()) {
                    for (item in playInfo) {
                        if (item.type == "high") {
                            val playUrl = item.url
                            mRootView?.playVideo(playUrl, bean.data.title!!)
                            break
                        }
                    }
                    mRootView?.showError("本视频需要消耗${(playInfo[0].urlList[0].size / 1024 / 1024).toFloat()}M流量")
                } else {
                    val playUrl = bean.data.playUrl
                    mRootView?.playVideo(playUrl!!, bean.data.title!!)
                }
                mRootView?.setVideoInfo(bean, fromWhere, bean.type)
            }
        }
    }
}