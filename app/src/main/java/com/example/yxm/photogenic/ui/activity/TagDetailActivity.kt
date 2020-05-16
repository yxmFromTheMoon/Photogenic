package com.example.yxm.photogenic.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lib_imageloader.ImageLoaderManager
import com.example.lib_network.bean.CategoryDetailBean
import com.example.lib_network.bean.CommonVideoBean
import com.example.lib_network.bean.HomeBean
import com.example.yxm.photogenic.R
import com.example.yxm.photogenic.base.BaseActivity
import com.example.yxm.photogenic.font.FontTextView
import com.example.yxm.photogenic.module.categorydetails.TagDetailContract
import com.example.yxm.photogenic.module.categorydetails.TagDetailPresenter
import com.example.yxm.photogenic.module.categorydetails.TagVideoAdapter
import com.example.yxm.photogenic.utils.AppBarStateChangeListener
import com.example.yxm.photogenic.widget.FooterView
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.gyf.immersionbar.ktx.immersionBar
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import kotlinx.android.synthetic.main.activity_category_details.*
import share.core.ShareManager

/**
 *Created by yxm on 2020/2/10
 *@function 标签详情页,点击视频tag跳转此页
 */
class TagDetailActivity : BaseActivity(), TagDetailContract.ITagDetailView {
    /**
     * data
     */
    private lateinit var tagBean: CommonVideoBean.ResultBean.ResultData.TagBean

    private val mAdapter: TagVideoAdapter by lazy {
        TagVideoAdapter()
    }
    private val mTagDetailPresenter: TagDetailPresenter by lazy {
        TagDetailPresenter()
    }

    init {
        mTagDetailPresenter.attachView(this)
    }

    /**
     * UI
     */
    private lateinit var mToolbar: Toolbar
    private lateinit var mAppbar: AppBarLayout
    private lateinit var mCollapsingToolbarLayout: CollapsingToolbarLayout
    private lateinit var mToolBarTitle: FontTextView
    private lateinit var mCategoryDetailRv: RecyclerView
    private lateinit var mCategoryBg: ImageView
    private lateinit var mCategoryName: FontTextView
    private lateinit var mCategoryTitle: TextView
    private lateinit var mRefreshLayout: SmartRefreshLayout
    private var loadMore = false

    override fun getLayoutId(): Int {
        return R.layout.activity_category_details
    }

    override fun initView() {
        mToolbar = tool_bar
        mAppbar = app_bar
        mToolBarTitle = toolbar_title
        setSupportActionBar(mToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        mCollapsingToolbarLayout = toolbar_layout
        mCategoryDetailRv = category_detail_rv
        mCategoryBg = category_bg
        mCategoryName = category_name
        mCategoryTitle = category_title
        mRefreshLayout = refreshLayout

        mRefreshLayout.setRefreshHeader(ClassicsHeader(mContext))

        mCategoryDetailRv.run {
            layoutManager = LinearLayoutManager(mContext)
            adapter = mAdapter
        }
        mAdapter.setFooterView(FooterView(mContext))
    }

    override fun initData() {
        ImageLoaderManager.displayImageWithPlaceholder(tagBean.headerImage, mCategoryBg, R.color.black)
        mCategoryName.text = tagBean.name
        mToolBarTitle.text = tagBean.name
        mCategoryTitle.text = tagBean.desc
        mTagDetailPresenter.getTagsVideo(tagBean.id.toLong())
    }

    override fun initListener() {

        mToolbar.setNavigationOnClickListener {
            finish()
        }

        mCategoryDetailRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val manager = mCategoryDetailRv.layoutManager as LinearLayoutManager
                val itemCount = mCategoryDetailRv.layoutManager!!.itemCount
                val lastVisibleItem = (manager).findLastVisibleItemPosition()
                if (!loadMore && (lastVisibleItem == (itemCount - 1))) {
                    loadMore = true
                    mTagDetailPresenter.loadMoreVideo()
                }
            }
        })

        //appbar滑动状态监听
        mAppbar.addOnOffsetChangedListener(object : AppBarStateChangeListener() {
            override fun onStateChanged(appBarLayout: AppBarLayout, state: State) {
                when (state) {
                    //初始化状态
                    State.IDLE -> {
                        mToolBarTitle.visibility = View.GONE
                        immersionBar {
                            titleBar(mToolbar)
                            statusBarDarkFont(false)
                        }
                    }
                    //折叠状态
                    State.COLLAPSED -> {
                        mToolBarTitle.visibility = View.VISIBLE
                        mToolbar.setNavigationIcon(R.mipmap.ic_action_back_black)
                        immersionBar {
                            titleBar(mToolbar)
                            statusBarDarkFont(true)
                        }
                    }
                    //展开状态
                    State.EXPANDED -> {
                        mToolBarTitle.visibility = View.GONE
                        mToolbar.setNavigationIcon(R.mipmap.ic_action_back_white)
                        immersionBar {
                            titleBar(mToolbar)
                            statusBarDarkFont(false)
                        }
                    }
                }
            }
        })

        mRefreshLayout.setOnRefreshListener {
            mTagDetailPresenter.getTagsVideo(tagBean.id.toLong())
        }

        //点击跳转播放
        mAdapter.setOnItemClickListener { adapter, _, position ->
            val bean = adapter.getItem(position) as CategoryDetailBean.FollowCardBean
            startActivity(Intent(mContext, VideoPlayActivity::class.java).apply {
                val bundle = Bundle()
                bundle.putSerializable("video", bean.data.content.data)
                bundle.putLong("relativeVideoId", bean.data.content.data.id)
                bundle.putInt("fromWhere", TAG_DETAIL)
                putExtras(bundle)
            })
        }

        //分享
        mAdapter.setOnItemChildClickListener { adapter, view, position ->
            val item = adapter.getItem(position) as HomeBean.Issue
            if (view.id == R.id.video_share_iv) {
                ShareManager.shareWebPage(mContext,
                        item.data.content?.data?.description ?: "",
                        item.data.content?.data?.title ?: "",
                        item.data.content?.data?.cover?.feed ?: "",
                        item.data.content?.data?.webUrl?.raw ?: "")
            }
        }

    }

    override fun initDataBeforeView() {
        super.initDataBeforeView()
        tagBean = intent.getSerializableExtra("tagBean") as CommonVideoBean.ResultBean.ResultData.TagBean
    }

    override fun showError(msg: String) {
        showErrorToast(msg)
        mCategoryDetailRv.visibility = View.GONE
    }

    override fun showSuccess() {
        mCategoryDetailRv.visibility = View.VISIBLE
    }

    override fun showLoading() {

    }

    override fun dismissLoading() {

    }

    override fun setTagVideo(data: ArrayList<CategoryDetailBean.FollowCardBean>) {
        mAdapter.setNewData(data)
    }

    override fun loadMoreVideo(data: ArrayList<CategoryDetailBean.FollowCardBean>) {
        loadMore = false
        mAdapter.addData(data)
    }

    override fun finishRefresh() {
        mRefreshLayout.finishRefresh()
    }

    override fun onDestroy() {
        super.onDestroy()
        mTagDetailPresenter.detachView()
    }

    companion object {
        const val TAG_DETAIL = 10
    }
}