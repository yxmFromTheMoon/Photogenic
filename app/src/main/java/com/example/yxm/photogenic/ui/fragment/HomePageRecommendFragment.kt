package com.example.yxm.photogenic.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.lib_network.bean.HomeBean
import share.core.ShareManager
import com.example.yxm.photogenic.R
import com.example.yxm.photogenic.base.BaseFragment
import com.example.yxm.photogenic.module.home.HomeRecommendAdapter
import com.example.yxm.photogenic.module.home.HomeRecommendContract
import com.example.yxm.photogenic.module.home.HomeRecommendPresenter
import com.example.yxm.photogenic.ui.activity.VideoPlayActivity
import com.example.yxm.photogenic.widget.FooterView
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import kotlinx.android.synthetic.main.fragment_homepage_recommend.*

/**
 * Created by yxm on 2020-1-14
 * @function:首页推荐fragment
 */
class HomePageRecommendFragment : BaseFragment(), HomeRecommendContract.IHomeRecommendView {

    private lateinit var mContentRv: RecyclerView
    private lateinit var mRefreshLayout: SmartRefreshLayout
    private var loadMore = false
    private lateinit var mAdapter: HomeRecommendAdapter

    private val mPresenter: HomeRecommendPresenter by lazy {
        HomeRecommendPresenter()
    }

    init {
        mPresenter.attachView(this)
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

    override fun finishRefresh() {
        mRefreshLayout.finishRefresh()
    }

    override fun setData(data: ArrayList<HomeBean.Issue>) {
        mAdapter = HomeRecommendAdapter(data)
        mAdapter.setFooterView(FooterView(mContext))
        mContentRv.run {
            layoutManager = LinearLayoutManager(mContext)
            adapter = mAdapter
        }

        mAdapter.setOnItemClickListener { adapter, view, position ->
            val type = adapter.getItemViewType(position)
            val item = adapter.getItem(position) as HomeBean.Issue

            val bundle = Bundle().apply {
                putSerializable("video", item)
                putInt("fromWhere", HOME_RECOMMEND)
            }
            when (type) {
                HomeBean.Issue.VIDEO_CARD -> {
                    startActivity(Intent(mContext, VideoPlayActivity::class.java).apply {
                        bundle.putLong("relativeVideoId", item.data.id!!.toLong())
                        putExtras(bundle)
                    })
                }
                HomeBean.Issue.FOLLOW_CARD -> {
                    startActivity(Intent(mContext, VideoPlayActivity::class.java).apply {
                        bundle.putLong("relativeVideoId", item.data.content!!.data.id)
                        putExtras(bundle)
                    })
                }
            }
        }

        mAdapter.setOnItemChildClickListener { adapter, view, position ->
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
                    "videoSmallCard" -> {
                        ShareManager.shareWebPage(mContext,
                                item.data.description ?: "",
                                item.data.title ?: "",
                                item.data.cover?.feed ?: "",
                                item.data.webUrl?.raw ?: "")
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

    override fun getLayoutId(): Int {
        return R.layout.fragment_homepage_recommend
    }

    /**
     * 初始化view
     */
    override fun initView(view: View) {
        mContentRv = home_recommend_rv
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
            mPresenter.getRecommendData()
        }
    }

    /**
     * 懒加载数据
     */
    override fun lazyLoad() {
        mPresenter.getRecommendData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter.detachView()
    }

    /**
     * 伴生对象
     */
    companion object {

        const val HOME_RECOMMEND = 5
        /**
         * 返回一个fragment实例
         */
        fun newInstance(): HomePageRecommendFragment = HomePageRecommendFragment()
    }
}