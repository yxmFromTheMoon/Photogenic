package com.example.yxm.photogenic.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.lib_network.bean.HomeBean
import com.example.lib_share.share.ShareManager
import com.example.yxm.photogenic.R
import com.example.yxm.photogenic.base.BaseFragment
import com.example.yxm.photogenic.module.home.HomeReportAdapter
import com.example.yxm.photogenic.module.home.HomeReportContract
import com.example.yxm.photogenic.module.home.HomeReportPresenter
import com.example.yxm.photogenic.ui.activity.VideoPlayActivity
import com.example.yxm.photogenic.widget.FooterView
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import kotlinx.android.synthetic.main.fragment_homepage_daily_report.*

/**
 * Created by yxm on 2020-1-14
 * @function:首页日报fragment
 */
class HomePageDailyReportFragment : BaseFragment(), HomeReportContract.IHomeReportView {

    private lateinit var mContentRv: RecyclerView
    private lateinit var mRefreshLayout: SmartRefreshLayout
    private var loadMore = false
    private lateinit var mAdapter: HomeReportAdapter

    private val mPresenter: HomeReportPresenter by lazy {
        HomeReportPresenter()
    }

    init {
        mPresenter.attachView(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_homepage_daily_report
    }

    /**
     * 初始化view
     */
    override fun initView(view: View) {
        mContentRv = home_report_rv
        mRefreshLayout = refreshLayout

        mRefreshLayout.setEnableLoadMore(false)
        mRefreshLayout.setRefreshHeader(ClassicsHeader(mContext))
    }

    override fun initListener() {
        //自动加载更多
        mContentRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val itemCount = mContentRv.layoutManager!!.itemCount
                val lastVisibleItem = (mContentRv.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                if (!loadMore && (lastVisibleItem == (itemCount - 1))) {
                    loadMore = true
                    mPresenter.loadMoreData()
                }
            }
        })

        mRefreshLayout.setOnRefreshListener {
            mPresenter.getReportData()
        }
    }

    /**
     * 懒加载数据
     */
    override fun lazyLoad() {
        mPresenter.getReportData()
    }

    override fun setData(data: ArrayList<HomeBean.Issue>) {
        mAdapter = HomeReportAdapter(data)
        mAdapter.setFooterView(FooterView(mContext))
        mContentRv.run {
            layoutManager = LinearLayoutManager(mContext)
            adapter = mAdapter
        }

        mAdapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
            val type = adapter.getItemViewType(position)
            val item = adapter.getItem(position) as HomeBean.Issue
            val bundle = Bundle().apply {
                putSerializable("video", item)
                putInt("fromWhere", HOME_REPORT)
            }
            when (type) {
                HomeBean.Issue.FOLLOW_CARD -> {
                    startActivity(Intent(mContext, VideoPlayActivity::class.java).apply {
                        bundle.putLong("relativeVideoId", item.data.content!!.data.id)
                        putExtras(bundle)
                    })
                }
            }
        }

        mAdapter.onItemChildClickListener = BaseQuickAdapter.OnItemChildClickListener { adapter, view, position ->
            val item = adapter.getItem(position) as HomeBean.Issue
            //分享区别数据类型
            if (view.id == R.id.video_share_iv) {
                when (item.type) {
                    "followCard" -> {
                        ShareManager.shareWebPage(mContext,
                                item.data.content?.data?.description ?: "",
                                item.data.content?.data?.title ?: "",
                                item.data.content?.data?.cover?.feed ?: "",
                                item.data.content?.data?.webUrl?.raw ?: "")
                    }
                }
            }
        }

        mAdapter.setNewData(data)
    }

    override fun setMoreData(data: ArrayList<HomeBean.Issue>) {
        loadMore = false
        mAdapter.addData(data)
    }

    override fun finishRefresh() {
        mRefreshLayout.finishRefresh()
    }

    override fun showLoading() {
    }

    override fun dismissLoading() {
    }

    override fun showError(msg: String) {
        showErrorToast(msg)
    }

    override fun showSuccess() {
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter.detachView()
    }

    /**
     * 伴生对象
     */
    companion object {
        const val HOME_REPORT = 4
        /**
         * 返回一个fragment实例
         */
        fun newInstance(): HomePageDailyReportFragment = HomePageDailyReportFragment()
    }
}