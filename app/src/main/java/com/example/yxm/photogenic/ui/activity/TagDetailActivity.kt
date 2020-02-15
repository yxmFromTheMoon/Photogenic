package com.example.yxm.photogenic.ui.activity

import android.support.design.widget.AppBarLayout
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.lib_imageloader.ImageLoaderManager
import com.example.lib_network.bean.CommonVideoBean
import com.example.yxm.photogenic.R
import com.example.yxm.photogenic.base.BaseActivity
import com.example.yxm.photogenic.font.FontTextView
import com.example.yxm.photogenic.module.categorydetails.RelativeVideoAdapter
import com.example.yxm.photogenic.module.categorydetails.TagDetailContract
import com.example.yxm.photogenic.module.categorydetails.TagDetailPresenter
import com.example.yxm.photogenic.utils.AppBarStateChangeListener
import com.example.yxm.photogenic.widget.FooterView
import com.gyf.immersionbar.ktx.immersionBar
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import kotlinx.android.synthetic.main.activity_category_details.*

/**
 *Created by yxm on 2020/2/10
 *@function 标签详情页,点击视频tag跳转此页
 */
class TagDetailActivity: BaseActivity(),TagDetailContract.ITagDetailView {
    /**
     * data
     */
    private lateinit var tagBean:CommonVideoBean.ResultBean.ResultData.TagBean
    private val mAdapter: RelativeVideoAdapter by lazy {
        RelativeVideoAdapter()
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
    private lateinit var mHintTv:FontTextView
    private lateinit var mToolbar: Toolbar
    private lateinit var mAppbar: AppBarLayout
    private lateinit var mCollapsingToolbarLayout: CollapsingToolbarLayout
    private lateinit var mToolBarTitle: FontTextView
    private lateinit var mCategoryDetailRv: RecyclerView
    private lateinit var mCategoryBg: ImageView
    private lateinit var mCategoryName: FontTextView
    private lateinit var mCategoryTitle: TextView
    private lateinit var mRefreshLayout: SmartRefreshLayout

    override fun getLayoutId(): Int {
        return R.layout.activity_category_details
    }

    override fun initView() {
        mToolbar = tool_bar
        mAppbar = app_bar
        mHintTv = category_title_tv
        mToolBarTitle = toolbar_title
        setSupportActionBar(mToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        mCollapsingToolbarLayout = toolbar_layout
        mCategoryDetailRv = category_detail_rv
        mCategoryBg = category_bg
        mCategoryName = category_name
        mCategoryTitle = category_title
        mRefreshLayout = refreshLayout
        mRefreshLayout.setEnableLoadMore(false)
        mRefreshLayout.setRefreshHeader(ClassicsHeader(mContext))

        mCategoryDetailRv.run {
            layoutManager = LinearLayoutManager(mContext)
            adapter = mAdapter
        }
        mAdapter.setFooterView(FooterView(mContext))
    }

    override fun initData() {
        ImageLoaderManager.displayImageForView(mCategoryBg,tagBean.headerImage)
        mCategoryName.text = tagBean.name
        mToolBarTitle.text = tagBean.name
        mCategoryTitle.text = tagBean.desc
        mTagDetailPresenter.getTagVideo(tagBean.id.toLong())
    }

    override fun initListener() {
        mToolbar.setNavigationOnClickListener {
            finish()
        }
        //appbar滑动状态监听
        mAppbar.addOnOffsetChangedListener(object : AppBarStateChangeListener() {
            override fun onStateChanged(appBarLayout: AppBarLayout, state: State) {
                when(state){
                    //初始化状态
                    State.IDLE ->{
                        mToolBarTitle.visibility = View.GONE
                        immersionBar {
                            titleBar(mToolbar)
                            statusBarDarkFont(false)
                        }
                    }
                    //折叠状态
                    State.COLLAPSED ->{
                        mToolBarTitle.visibility = View.VISIBLE
                        mToolbar.setNavigationIcon(R.mipmap.ic_action_back_black)
                        immersionBar {
                            titleBar(mToolbar)
                            statusBarDarkFont(true)
                        }
                    }
                    //展开状态
                    State.EXPANDED ->{
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
            mTagDetailPresenter.getTagVideo(tagBean.id.toLong())
        }
    }

    override fun initDataBeforeView() {
        super.initDataBeforeView()
        tagBean = intent.getSerializableExtra("tagBean") as CommonVideoBean.ResultBean.ResultData.TagBean
    }

    override fun showError(msg: String) {
        showErrorToast(msg)
        mHintTv.visibility = View.GONE
        mCategoryDetailRv.visibility = View.GONE
    }

    override fun showSuccess() {
        mHintTv.visibility = View.VISIBLE
        mCategoryDetailRv.visibility = View.VISIBLE
    }

    override fun showLoading() {
        mCategoryDetailRv.visibility = View.GONE
        mHintTv.visibility = View.GONE
    }

    override fun dismissLoading() {
        mCategoryDetailRv.visibility = View.VISIBLE
        mHintTv.visibility = View.VISIBLE
    }

    override fun setVideo(data: ArrayList<CommonVideoBean.ResultBean>) {
        mAdapter.setNewData(data)
    }

    override fun finishRefresh() {
        mRefreshLayout.finishRefresh()
    }

    override fun onDestroy() {
        super.onDestroy()
        mTagDetailPresenter.detachView()
    }
}