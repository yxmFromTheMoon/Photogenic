package com.example.yxm.photogenic.ui.fragment

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.lib_network.bean.HomeRecommendBean
import com.example.yxm.photogenic.R
import com.example.yxm.photogenic.base.BaseFragment
import com.example.yxm.photogenic.module.home.HomeRecommendAdapter
import com.example.yxm.photogenic.module.home.HomeRecommendContract
import com.example.yxm.photogenic.module.home.HomeRecommendPresenter
import com.example.yxm.photogenic.widget.FooterView
import kotlinx.android.synthetic.main.fragment_homepage_recommend.*

/**
 * Created by yxm on 2020-1-14
 * @function:首页推荐fragment
 */
class HomePageRecommendFragment : BaseFragment(), HomeRecommendContract.IHomeRecommendView {

    private lateinit var mContentRv: RecyclerView
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

    override fun setData(data: ArrayList<HomeRecommendBean.Issue>) {
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
                HomeRecommendBean.Issue.VIDEO_CARD -> {
                    showErrorToast("type + $type")
                }
                HomeRecommendBean.Issue.FOLLOW_CARD -> {
                    showErrorToast("type + $type")
                }
                HomeRecommendBean.Issue.SQUARE_CARD -> {
                    showErrorToast("type + $type")
                }
            }
        }

        mAdapter.setNewData(data)
    }

    override fun setMoreData(data: ArrayList<HomeRecommendBean.Issue>) {
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
    }

    /**
     * 懒加载数据
     */
    override fun lazyLoad() {
        mPresenter.getRecommendData()
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