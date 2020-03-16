package com.example.yxm.photogenic.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.lib_network.bean.CommunityBean
import com.example.lib_share.share.ShareManager
import com.example.yxm.photogenic.R
import com.example.yxm.photogenic.base.BaseFragment
import com.example.yxm.photogenic.module.community.CommunityRecommendAdapter
import com.example.yxm.photogenic.module.community.CommunityRecommendContract
import com.example.yxm.photogenic.module.community.CommunityRecommendPresenter
import com.example.yxm.photogenic.ui.activity.PicturePreviewActivity
import com.example.yxm.photogenic.ui.activity.VideoPlayActivity
import com.example.yxm.photogenic.utils.MathHelper
import com.example.yxm.photogenic.widget.FooterView
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import kotlinx.android.synthetic.main.fragment_community_recommend.*

/**
 * Created by yxm on 2020-1-14
 * @function:社区推荐fragment
 */
class CommunityRecommendFragment : BaseFragment(), CommunityRecommendContract.ICommunityRecommendView {

    private lateinit var mRefreshLayout: SmartRefreshLayout
    private lateinit var mContentRv: RecyclerView
    private var loadMore = false

    private val mAdapter: CommunityRecommendAdapter by lazy {
        CommunityRecommendAdapter()
    }

    private val mPresenter: CommunityRecommendPresenter by lazy {
        CommunityRecommendPresenter()
    }

    init {
        mPresenter.attachView(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_community_recommend
    }

    override fun initView(view: View) {
        mRefreshLayout = refreshLayout
        mContentRv = community_recommend_rv

        mRefreshLayout.setRefreshHeader(ClassicsHeader(mContext))
        mRefreshLayout.setEnableLoadMore(false)
        mAdapter.setFooterView(FooterView(mContext))

        mContentRv.run {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = mAdapter
        }
    }

    override fun initListener() {
        mRefreshLayout.setOnRefreshListener {
            mPresenter.getRecommendData()
        }

        //自动加载更多
        mContentRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val manager = mContentRv.layoutManager as StaggeredGridLayoutManager
                val itemCount = mContentRv.layoutManager!!.itemCount
                val lastVisibleItem = (manager).findLastVisibleItemPositions(IntArray(manager.spanCount))
                val lastVisiblePosition = MathHelper.getMaxElem(lastVisibleItem)
                if (!loadMore && (lastVisiblePosition == (itemCount - 1))) {
                    loadMore = true
                    mPresenter.loadMoreData()
                }
            }
        })

        mAdapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, _, position ->
            val issue = adapter.getItem(position) as CommunityBean.Issue
            val bundle = Bundle().apply {
                putSerializable("video", issue)
                putLong("relativeVideoId", issue.id)
                putInt("fromWhere", COMMUNITY_RECOMMEND)
            }
            when (issue.type) {
                "pictureFollowCard" -> {
                    startActivity(Intent(mContext, PicturePreviewActivity::class.java).apply {
                        val urls = Bundle().apply {
                            putStringArrayList("urls", issue.data.content.data.urls)
                            putLong("width",issue.data.content.data.width)
                            putLong("height",issue.data.content.data.height)
                        }
                        putExtras(urls)
                    })
                }
                "autoPlayFollowCard" -> {
                    startActivity(Intent(mContext, VideoPlayActivity::class.java).apply {
                        putExtras(bundle)
                    })
                }
            }
        }

        //分享
        mAdapter.onItemChildClickListener = BaseQuickAdapter.OnItemChildClickListener { adapter, _, position ->
            val issue = adapter.getItem(position) as CommunityBean.Issue
            when (issue.type) {
                "pictureFollowCard" -> {
                    ShareManager.shareImage(mContext, issue.data.content.data.url ?: "")
                }
                "autoPlayFollowCard" -> {
                    ShareManager.shareWebPage(mContext,
                            issue.data.content.data.description,
                            "@${issue.data.header.issuerName}",
                            issue.data.content.data.cover.feed ?: "",
                            issue.data.content.data.playUrl ?: "")
                }
            }
        }
    }

    override fun lazyLoad() {
        mPresenter.getRecommendData()
    }

    override fun setData(data: ArrayList<CommunityBean.Issue>) {
        mAdapter.setNewData(data)
    }

    override fun setMoreData(data: ArrayList<CommunityBean.Issue>) {
        loadMore = false
        mAdapter.addData(data)
    }

    override fun finishRefresh() {
        mRefreshLayout.finishRefresh()
    }

    override fun showLoading() {
        mContentRv.visibility = View.GONE
    }

    override fun dismissLoading() {
        mContentRv.visibility = View.VISIBLE
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

        const val COMMUNITY_RECOMMEND = 2
        /**
         * 返回一个fragment实例
         */
        fun newInstance(): CommunityRecommendFragment = CommunityRecommendFragment()
    }
}