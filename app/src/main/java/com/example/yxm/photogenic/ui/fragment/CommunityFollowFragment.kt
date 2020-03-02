package com.example.yxm.photogenic.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.lib_network.bean.CommonVideoBean
import com.example.lib_share.share.ShareManager
import com.example.yxm.photogenic.R
import com.example.yxm.photogenic.base.BaseFragment
import com.example.yxm.photogenic.module.community.CommunityFollowAdapter
import com.example.yxm.photogenic.module.community.CommunityFollowContract
import com.example.yxm.photogenic.module.community.CommunityFollowPresenter
import com.example.yxm.photogenic.ui.activity.VideoPlayActivity
import com.example.yxm.photogenic.widget.FooterView
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import kotlinx.android.synthetic.main.fragment_community_follow.*

/**
 * Created by yxm on 2020-1-14
 * @function:社区关注fragment
 */
class CommunityFollowFragment : BaseFragment(), CommunityFollowContract.ICommunityFollowView {

    private lateinit var mRefreshLayout: SmartRefreshLayout
    private lateinit var mContentRv: RecyclerView
    private var loadMore = false

    private val mPresenter: CommunityFollowPresenter by lazy {
        CommunityFollowPresenter()
    }

    private val mAdapter: CommunityFollowAdapter by lazy {
        CommunityFollowAdapter()
    }

    init {
        mPresenter.attachView(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_community_follow
    }

    override fun initView(view: View) {
        mContentRv = community_follow_rv
        mRefreshLayout = refreshLayout

        mRefreshLayout.setEnableLoadMore(false)
                .setOnRefreshListener {
                    mPresenter.getFollowData()
                }
                .setRefreshHeader(ClassicsHeader(mContext))
        mContentRv.apply {
            layoutManager = LinearLayoutManager(mContext)
            adapter = mAdapter
        }
        mAdapter.setFooterView(FooterView(mContext))
    }

    override fun initListener() {
        mAdapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
            val item = adapter.getItem(position) as CommonVideoBean.ResultBean
            startActivity(Intent(mContext, VideoPlayActivity::class.java).apply {
                val bundle = Bundle()
                bundle.putSerializable("video", item)
                bundle.putInt("fromWhere", COMMUNITY_FOLLOW)
                bundle.putLong("relativeVideoId", item.data.id)
                putExtras(bundle)
            })
        }
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

        mContentRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val manager = mContentRv.layoutManager as LinearLayoutManager
                val itemCount = mContentRv.layoutManager!!.itemCount
                val lastVisibleItem = (manager).findLastVisibleItemPosition()
                if (!loadMore && (lastVisibleItem == (itemCount - 1))) {
                    loadMore = true
                    mPresenter.loadMoreData()
                }
            }
        })
    }

    override fun lazyLoad() {
        mPresenter.getFollowData()
    }

    override fun setData(data: ArrayList<CommonVideoBean.ResultBean>) {
        mAdapter.setNewData(data)
    }

    override fun setMoreData(data: ArrayList<CommonVideoBean.ResultBean>) {
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

        const val COMMUNITY_FOLLOW = 1
        /**
         * 返回一个fragment实例
         */
        fun newInstance(): CommunityFollowFragment = CommunityFollowFragment()
    }

}