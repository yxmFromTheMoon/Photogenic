package com.example.yxm.photogenic.ui.fragment

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.lib_network.bean.CommunityBean
import com.example.yxm.photogenic.R
import com.example.yxm.photogenic.base.BaseFragment
import com.example.yxm.photogenic.module.community.CommunityRecommendAdapter
import com.example.yxm.photogenic.module.community.CommunityRecommendContract
import com.example.yxm.photogenic.module.community.CommunityRecommendPresenter
import com.example.yxm.photogenic.utils.MathHelper
import com.example.yxm.photogenic.widget.FooterView
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import kotlinx.android.synthetic.main.fragment_community_recommend.*

/**
 * Created by yxm on 2020-1-14
 * @function:社区推荐fragment
 */
class CommunityRecommendFragment: BaseFragment(),CommunityRecommendContract.ICommunityRecommendView {

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
            layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
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

        mAdapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
            val issue = adapter.getItem(position) as CommunityBean.Issue
            when(issue.type){
                "pictureFollowCard" -> {
                    showErrorToast("图片类型")
                }
                "autoPlayFollowCard" -> {
                    showErrorToast("视频类型")
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