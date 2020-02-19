package com.example.yxm.photogenic.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.lib_network.bean.CategoriesBean
import com.example.lib_network.bean.CommonVideoBean
import com.example.yxm.photogenic.R
import com.example.yxm.photogenic.base.BaseActivity
import com.example.yxm.photogenic.module.categorydetails.CategoryAllAdapter
import com.example.yxm.photogenic.module.discovery.DiscoveryContract
import com.example.yxm.photogenic.module.discovery.DiscoveryPresenter
import com.example.yxm.photogenic.widget.FooterView
import com.example.yxm.photogenic.widget.LeftBackTitle
import com.gyf.immersionbar.ktx.immersionBar
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import kotlinx.android.synthetic.main.activity_all_categories.*
import kotlinx.android.synthetic.main.fragment_discovery.hot_category_rv
import kotlinx.android.synthetic.main.fragment_discovery.refreshLayout
import java.io.Serializable

/**
 *Created by yxm on 2020/2/13
 *@function 查看全部分类activity
 */
class AllCategoriesActivity: BaseActivity(),DiscoveryContract.IDiscoveryView {

    /**
     * data
     */
    private val mPresenter: DiscoveryPresenter by lazy {
        DiscoveryPresenter()
    }
    private val mCategoryAdapter: CategoryAllAdapter by lazy {
        CategoryAllAdapter()
    }

    init {
        mPresenter.attachView(this)
    }

    private lateinit var mRefreshLayout: SmartRefreshLayout
    private lateinit var mCategoryRv: RecyclerView
    private lateinit var mLeftBackTitle: LeftBackTitle

    override fun getLayoutId(): Int {
        return R.layout.activity_all_categories
    }

    override fun initView() {
        mCategoryRv = hot_category_rv
        mRefreshLayout = refreshLayout
        mLeftBackTitle = header_title
        mRefreshLayout.setOnRefreshListener {
            mPresenter.getCategoryData()
        }

        mRefreshLayout.setEnableLoadMore(false)
        mCategoryRv.run {
            layoutManager = GridLayoutManager(mContext,2,GridLayoutManager.VERTICAL,false)
            adapter = mCategoryAdapter
        }
        mCategoryAdapter.setFooterView(FooterView(mContext))
    }

    override fun initData() {
        mPresenter.getCategoryData()
    }

    override fun initListener() {
        mLeftBackTitle.setLeftFinish(this)

        mRefreshLayout.setOnLoadMoreListener {
            mPresenter.getCategoryData()
        }

        mCategoryAdapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
            val categoryBean = adapter.getItem(position) as Serializable
            startActivity(Intent(mContext,CategoryDetailActivity::class.java).apply {
                val bundle = Bundle()
                bundle.putSerializable("categoryBean",categoryBean)
                putExtras(bundle)
            })
        }
    }

    override fun setStatusBarState() {
        super.setStatusBarState()
        immersionBar {
            titleBar(mLeftBackTitle)
            statusBarDarkFont(true)
        }
    }

    override fun setBannerList(data: ArrayList<String>) {

    }

    override fun setCategory(data: ArrayList<CategoriesBean>) {
        mCategoryAdapter.setNewData(data)
    }

    override fun setVideoData(data: ArrayList<CommonVideoBean.ResultBean.ResultData>) {

    }

    override fun finishRefresh() {
        mRefreshLayout.finishRefresh()
    }

    override fun showLoading() {
        mCategoryRv.visibility = View.GONE
    }

    override fun dismissLoading() {
        mCategoryRv.visibility = View.VISIBLE
    }

    override fun showError(msg: String) {

    }

    override fun showSuccess() {

    }
}