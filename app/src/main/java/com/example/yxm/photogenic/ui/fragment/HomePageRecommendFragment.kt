package com.example.yxm.photogenic.ui.fragment

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.lib_network.bean.HomeBean
import com.example.lib_share.share.ShareDialog
import com.example.yxm.photogenic.R
import com.example.yxm.photogenic.base.BaseFragment
import com.example.yxm.photogenic.module.home.HomeRecommendAdapter
import com.example.yxm.photogenic.module.home.HomeRecommendContract
import com.example.yxm.photogenic.module.home.HomeRecommendPresenter
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

        mAdapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
            val type = adapter.getItemViewType(position)
            val item = adapter.getItem(position)
            when (type) {
                HomeBean.Issue.VIDEO_CARD -> {
                    showErrorToast("type + $type")
                }
                HomeBean.Issue.FOLLOW_CARD -> {
                    showErrorToast("type + $type")
                }
                HomeBean.Issue.SQUARE_CARD -> {
                    showErrorToast("type + $type")
                }
            }
        }
        
        mAdapter.onItemChildClickListener = BaseQuickAdapter.OnItemChildClickListener { adapter, view, position ->
            when(view.id){
                R.id.video_share_iv -> {
                    val dialog = ShareDialog(mContext)
                    dialog.mUrl = "https://www.baidu.com"
                    dialog.show()
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
        /**
         * 返回一个fragment实例
         */
        fun newInstance(): HomePageRecommendFragment = HomePageRecommendFragment()
    }
}