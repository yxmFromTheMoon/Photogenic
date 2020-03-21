package com.example.yxm.photogenic.ui.activity

import android.content.res.Configuration
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.lib_imageloader.ImageLoaderManager
import com.example.lib_network.bean.CommonVideoBean
import com.example.lib_network.bean.CommunityBean
import com.example.lib_network.bean.HomeBean
import com.example.lib_share.share.ShareManager
import com.example.yxm.photogenic.R
import com.example.yxm.photogenic.base.BaseActivity
import com.example.yxm.photogenic.font.FontTextView
import com.example.yxm.photogenic.module.videodetail.VideoDetailContract
import com.example.yxm.photogenic.module.videodetail.VideoDetailPresenter
import com.example.yxm.photogenic.module.videodetail.VideoDetailRelativeAdapter
import com.example.yxm.photogenic.ui.activity.CategoryDetailActivity.Companion.CATEGORY_DETAIL
import com.example.yxm.photogenic.ui.activity.TagDetailActivity.Companion.TAG_DETAIL
import com.example.yxm.photogenic.ui.fragment.CommunityFollowFragment.Companion.COMMUNITY_FOLLOW
import com.example.yxm.photogenic.ui.fragment.CommunityRecommendFragment.Companion.COMMUNITY_RECOMMEND
import com.example.yxm.photogenic.ui.fragment.DiscoveryFragment.Companion.DISCOVERY
import com.example.yxm.photogenic.ui.fragment.HomePageDailyReportFragment.Companion.HOME_REPORT
import com.example.yxm.photogenic.ui.fragment.HomePageRecommendFragment.Companion.HOME_RECOMMEND
import com.example.yxm.photogenic.ui.fragment.RankFragment.Companion.RANK_FRAGMENT
import com.example.yxm.photogenic.ui.fragment.SearchResultFragment.Companion.SEARCH_RESULT
import com.example.yxm.photogenic.widget.FooterView
import com.gyf.immersionbar.ImmersionBar
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer
import de.hdodenhof.circleimageview.CircleImageView
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_video_play.*

/**
 *Created by yxm on 2020/2/25
 *@function 视频播放页
 */
class VideoPlayActivity : BaseActivity(), VideoDetailContract.IVideoDetailView {
    /**
     * data
     */
    private val mPresenter: VideoDetailPresenter by lazy {
        VideoDetailPresenter()
    }

    private val mAdapter: VideoDetailRelativeAdapter by lazy {
        VideoDetailRelativeAdapter()
    }
    private lateinit var video: Any
    private var orientationUtils: OrientationUtils? = null
    private var isPause = false
    private var isPlay = false
    private var fromWhere: Int = 0
    private var relativeVideoId: Long = 187512
    private var permission: Disposable? = null

    /**
     * UI
     */
    private lateinit var mVideoPlayer: StandardGSYVideoPlayer
    private lateinit var mVideoTitle: FontTextView
    private lateinit var mVideoCategory: TextView
    private lateinit var mVideoIntroduce: TextView
    private lateinit var mAuthorAvatar: CircleImageView
    private lateinit var mVideoAuthorTitle: FontTextView
    private lateinit var mVideoDescription: TextView
    private lateinit var mRelativeVideoRv: RecyclerView
    private lateinit var mBackgroundView: NestedScrollView

    init {
        mPresenter.attachView(this)
    }

    override fun setStatusBarState() {
        super.setStatusBarState()
        ImmersionBar.hideStatusBar(this.window)
    }

    override fun initDataBeforeView() {
        super.initDataBeforeView()
        fromWhere = intent.getIntExtra("fromWhere", 0)
        video = intent.getSerializableExtra("video")
        relativeVideoId = intent.getLongExtra("relativeVideoId", relativeVideoId)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_video_play
    }

    override fun initView() {
        mVideoPlayer = video_player
        mVideoTitle = video_title
        mVideoAuthorTitle = video_author_title
        mVideoCategory = video_category
        mVideoDescription = video_description
        mVideoIntroduce = video_introduce
        mAuthorAvatar = video_author_avatar
        mBackgroundView = video_detail_nested_scroll

        mRelativeVideoRv = relative_video_rv
        mRelativeVideoRv.run {
            layoutManager = LinearLayoutManager(mContext)
            adapter = mAdapter
        }

        mAdapter.setFooterView(FooterView(mContext).apply {
            setFooterColor(R.color.white)
        })
        initVideoPlayer()
    }

    override fun initData() {
        permission = rxPermission.request("android.permission.READ_EXTERNAL_STORAGE")
                .subscribe {
                    if (it) {
                        mPresenter.loadVideoInfo(video, fromWhere)
                    }
                }
        mPresenter.loadRelativeVideo(relativeVideoId)
    }

    override fun initListener() {
        mAdapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, _, position ->
            val item = adapter.getItem(position) as CommonVideoBean.ResultBean
            playVideo(item.data.playUrl, item.data.title)
            mPresenter.loadRelativeVideo(item.data.id)
            mPresenter.loadVideoInfo(item, VIDEO_DETAIL)
        }
        //分享
        mAdapter.onItemChildClickListener = BaseQuickAdapter.OnItemChildClickListener { adapter, view, position ->
            val item = adapter.getItem(position) as CommonVideoBean.ResultBean
            if (view.id == R.id.video_share_iv) {
                ShareManager.shareWebPage(mContext,
                        item.data.description,
                        item.data.title,
                        item.data.cover.feed ?: "",
                        item.data.webUrl.raw)
            }
        }
    }

    /**
     * 设置视频信息
     */
    override fun setVideoInfo(video: Any, fromWhere: Int, type: String) {
        when (fromWhere) {
            COMMUNITY_FOLLOW, VIDEO_DETAIL, RANK_FRAGMENT,
            DISCOVERY, SEARCH_RESULT, CATEGORY_DETAIL, TAG_DETAIL -> {
                val bean = video as CommonVideoBean.ResultBean.ResultData
                setVideoInfo(bean)
            }
            HOME_RECOMMEND, HOME_REPORT -> {
                if (type == "followCard") {
                    val data = video as CommonVideoBean.ResultBean.ResultData
                    setVideoInfo(data)
                } else {
                    val bean = video as HomeBean.Issue
                    mVideoTitle.text = bean.data.title
                    mVideoAuthorTitle.text = bean.data.author?.name
                    mVideoCategory.text = "#${bean.data.category}"
                    mVideoDescription.text = bean.data.author?.description
                    mVideoIntroduce.text = bean.data.description
                    Glide.with(this).load(bean.data.author?.icon)
                            .into(mAuthorAvatar)
                    ImageLoaderManager.displayImageForViewGroup(mBackgroundView, bean.data.cover?.blurred
                            ?: "")
                }
            }
            COMMUNITY_RECOMMEND -> {
                val bean = video as CommunityBean.Issue
                mVideoTitle.text = bean.data.content.data.title
                mVideoAuthorTitle.text = bean.data.content.data.owner.nickname
                mVideoCategory.text = "#${bean.data.header.issuerName}"
                mVideoDescription.text = bean.data.content.data.owner.description
                mVideoIntroduce.text = bean.data.content.data.description
                Glide.with(this).load(bean.data.content.data.owner.avatar)
                        .into(mAuthorAvatar)
                ImageLoaderManager.displayImageForViewGroup(mBackgroundView, bean.data.content.data.cover.blurred
                        ?: "")
            }
        }
    }

    override fun setRelativeVideo(data: ArrayList<CommonVideoBean.ResultBean>) {
        mAdapter.setNewData(data)
    }

    override fun playVideo(url: String, title: String) {
        mVideoPlayer.setUp(url, false, title)
        //自动播放
        mVideoPlayer.startPlayLogic()
    }

    override fun showLoading() {
        mRelativeVideoRv.visibility = View.GONE
    }

    override fun dismissLoading() {
        mRelativeVideoRv.visibility = View.VISIBLE
    }

    override fun showError(msg: String) {
        showErrorToast(msg)
    }

    override fun showSuccess() {
    }

    override fun onPause() {
        super.onPause()
        isPause = true
        mVideoPlayer.onVideoPause()
    }

    override fun onResume() {
        super.onResume()
        getCurrentPlay().onVideoResume()
        isPause = false
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isPlay) {
            getCurrentPlay().release()
        }
        orientationUtils?.releaseListener()
        permission?.dispose()
    }

    override fun onBackPressed() {
        orientationUtils?.backToProtVideo()
        //如果是横屏则返回正常状态
        if (GSYVideoManager.backFromWindowFull(this)) {
            return
        }
        mVideoPlayer.setVideoAllCallBack(null)
        GSYVideoManager.releaseAllVideos()
        super.onBackPressed()
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        //如果旋转了就全屏
        if (isPlay && !isPause) {
            mVideoPlayer.onConfigurationChanged(this, newConfig, orientationUtils, true, true)
        }
    }

    //获取当前的播放器
    private fun getCurrentPlay(): GSYVideoPlayer {
        if (mVideoPlayer.fullWindowPlayer != null) {
            return mVideoPlayer.fullWindowPlayer
        }
        return mVideoPlayer
    }

    /**
     * 初始化videoPlayer配置
     */
    private fun initVideoPlayer() {
        //设置旋转
        orientationUtils = OrientationUtils(this, mVideoPlayer)
        orientationUtils!!.isEnable = false
        mVideoPlayer.apply {
            //不需要流量提醒
            isNeedShowWifiTip = false
            isRotateViewAuto = false
            //小屏状态下不可以滑动调整
            setIsTouchWiget(false)
            //全屏状态下可以滑动调整
            setIsTouchWigetFull(true)
            isShowFullAnimation = false
            //是否开启锁屏
            isNeedLockFull = true
            //退出界面时释放当前播放的video
            backButton.setOnClickListener {
                this.release()
                onBackPressed()
            }
            //全屏播放
            fullscreenButton.setOnClickListener {
                orientationUtils!!.resolveByClick()
                this.startWindowFullscreen(this@VideoPlayActivity, true, false)
            }
            setVideoAllCallBack(object : GSYSampleCallBack() {

                override fun onPrepared(url: String?, vararg objects: Any?) {
                    super.onPrepared(url, *objects)
                    //开始播放才能旋转
                    orientationUtils!!.isEnable = true
                    isPlay = true
                }

                override fun onQuitFullscreen(url: String?, vararg objects: Any?) {
                    super.onQuitFullscreen(url, *objects)
                    orientationUtils?.backToProtVideo()
                }

                override fun onPlayError(url: String?, vararg objects: Any?) {
                    super.onPlayError(url, *objects)
                    showErrorToast("播放失败")
                }
            })
            setLockClickListener { _, lock ->
                orientationUtils?.isEnable = !lock
            }
        }
    }

    /**
     * 设置视频信息
     */
    private fun setVideoInfo(data: CommonVideoBean.ResultBean.ResultData) {
        mVideoTitle.text = data.title
        mVideoAuthorTitle.text = data.author?.name
        mVideoCategory.text = "#${data.category}"
        mVideoDescription.text = data.author?.description
        mVideoIntroduce.text = data.description
        Glide.with(this).load(data.author?.icon)
                .into(mAuthorAvatar)
        ImageLoaderManager.displayImageForViewGroup(mBackgroundView, data.cover.blurred
                ?: "")
    }

    companion object {
        const val VIDEO_DETAIL = 9
    }
}