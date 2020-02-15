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
import com.example.lib_network.bean.CategoriesBean
import com.example.lib_network.bean.CategoryDetailBean
import com.example.yxm.photogenic.R
import com.example.yxm.photogenic.base.BaseActivity
import com.example.yxm.photogenic.font.FontTextView
import com.example.yxm.photogenic.module.categorydetails.CategoryDetailContract
import com.example.yxm.photogenic.module.categorydetails.CategoryDetailPresenter
import com.example.yxm.photogenic.module.categorydetails.CategoryVideoAdapter
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
class CategoryDetailActivity: BaseActivity(),CategoryDetailContract.ICategoryDetailView {

    private val mAdapter: CategoryVideoAdapter by lazy {
        CategoryVideoAdapter()
    }

    private val mCategoryDetailPresenter: CategoryDetailPresenter by lazy {
        CategoryDetailPresenter()
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
        mRefreshLayout.setEnableLoadMore(false)
        mRefreshLayout.setRefreshHeader(ClassicsHeader(mContext))

        mCategoryDetailRv.run {
            layoutManager = LinearLayoutManager(mContext)
            adapter = mAdapter
        }
        mAdapter.setFooterView(FooterView(mContext))
    }

    override fun initData() {
        ImageLoaderManager.displayImageForView(mCategoryBg,categoryBean.headerImage)
        mCategoryName.text = categoryBean.name
        mToolBarTitle.text = categoryBean.name
        mCategoryTitle.text = categoryBean.description
        mCategoryDetailPresenter.getVideo(categoryBean.id)
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
            mCategoryDetailPresenter.getVideo(categoryBean.id)
        }
    }

    override fun initDataBeforeView() {
        super.initDataBeforeView()
        categoryBean = intent.getSerializableExtra("categoryBean") as CategoriesBean
    }

    override fun setVideo(data: ArrayList<CategoryDetailBean.FollowCardBean>) {
        mAdapter.setNewData(data)
    }

    override fun setMoreVideo(data: ArrayList<CategoryDetailBean.FollowCardBean>) {
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
}