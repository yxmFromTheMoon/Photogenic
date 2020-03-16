package com.example.yxm.photogenic.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.lib_imageloader.ImageLoaderManager
import com.example.lib_network.bean.CategoriesBean
import com.example.lib_network.bean.CategoryDetailBean
import com.example.lib_share.share.ShareManager
import com.example.yxm.photogenic.R
import com.example.yxm.photogenic.base.BaseActivity
import com.example.yxm.photogenic.font.FontTextView
import com.example.yxm.photogenic.module.categorydetails.*
import com.example.yxm.photogenic.utils.AppBarStateChangeListener
import com.example.yxm.photogenic.widget.FooterView
import com.gyf.immersionbar.ktx.immersionBar
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import kotlinx.android.synthetic.main.activity_category_details.*

/**
 *Created by yxm on 2020/2/10
 *@function 分类详情Activity
 */
class CategoryDetailActivity : BaseActivity(), TagDetailContract.ITagDetailView {

    private val mAdapter: TagVideoAdapter by lazy {
        TagVideoAdapter()
    }

    private val mCategoryDetailPresenter: TagDetailPresenter by lazy {
        TagDetailPresenter()
    }

    init {
        mCategoryDetailPresenter.attachView(this)
    }

    /**
     * UI
     */
    private lateinit var categoryBean: CategoriesBean
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
        ImageLoaderManager.displayImageForView(mCategoryBg, categoryBean.headerImage)
        mCategoryName.text = categoryBean.name
        mToolBarTitle.text = categoryBean.name
        mCategoryTitle.text = categoryBean.description
        mCategoryDetailPresenter.getTagsVideo(categoryBean.tagId.toLong())
    }

    override fun initListener() {
        mToolbar.setNavigationOnClickListener {
            finish()
        }

        //解决recyclerview和nestscrollview滑动冲突，
        //首先给recyclerview设置isNestedScrollingEnabled = false,将滑动时将交给nestscrollview去处理
        //再监听nestscrollview的滑动事件去加载更多
        nested_scroll_view.setOnScrollChangeListener { nestedScrollView: NestedScrollView, _: Int, scrollY: Int, _: Int, _: Int ->
            //判断是否滑到底部
            if (scrollY == (nestedScrollView.getChildAt(0).measuredHeight - nestedScrollView.measuredHeight)) {
                mCategoryDetailPresenter.loadMoreVideo()
            }
        }

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
            mCategoryDetailPresenter.getTagsVideo(categoryBean.tagId.toLong())
        }

        mAdapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, _, position ->
            val item = adapter.getItem(position) as CategoryDetailBean.FollowCardBean
            val bundle = Bundle().apply {
                putSerializable("video", item.data.content.data)
                putLong("relativeVideoId", item.data.content.data.id)
                putInt("fromWhere", CATEGORY_DETAIL)
            }
            startActivity(Intent(mContext, VideoPlayActivity::class.java).apply {
                putExtras(bundle)
            })
        }
        //分享
        mAdapter.onItemChildClickListener = BaseQuickAdapter.OnItemChildClickListener { adapter, _, position ->
            val item = adapter.getItem(position) as CategoryDetailBean.FollowCardBean
            ShareManager.shareWebPage(mContext,
                    item.data.content.data.description,
                    item.data.content.data.title,
                    item.data.content.data.cover.feed ?: "",
                    item.data.content.data.webUrl.raw)
        }
    }

    override fun initDataBeforeView() {
        super.initDataBeforeView()
        categoryBean = intent.getSerializableExtra("categoryBean") as CategoriesBean
    }

    override fun setTagVideo(data: ArrayList<CategoryDetailBean.FollowCardBean>) {
        mAdapter.setNewData(data)
    }

    override fun loadMoreVideo(data: ArrayList<CategoryDetailBean.FollowCardBean>) {
        mAdapter.addData(data)
    }

    override fun finishRefresh() {
        mRefreshLayout.finishRefresh()
    }

    override fun showError(msg: String) {
        showErrorToast(msg)
    }

    override fun showLoading() {
        mCategoryDetailRv.visibility = View.GONE
    }

    override fun dismissLoading() {
        mCategoryDetailRv.visibility = View.VISIBLE
    }

    override fun showSuccess() {

    }

    override fun onDestroy() {
        super.onDestroy()
        mCategoryDetailPresenter.detachView()
    }

    companion object {
        const val CATEGORY_DETAIL = 8
    }
}