package com.example.yxm.photogenic.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.lib_network.bean.CommonVideoBean
import com.example.yxm.photogenic.R
import com.example.yxm.photogenic.base.BaseFragment
import com.example.yxm.photogenic.module.categorydetails.RelativeVideoAdapter
import com.example.yxm.photogenic.module.rank.RankContract
import com.example.yxm.photogenic.module.rank.RankPresenter
import com.example.yxm.photogenic.widget.FooterView
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import kotlinx.android.synthetic.main.fragment_discovery.*
import kotlinx.android.synthetic.main.fragment_discovery.refreshLayout
import kotlinx.android.synthetic.main.fragment_rank.*

/**
 * Created by yxm on 2020-1-15
 * @function: 周、月、总排行榜
 */
class RankFragment: BaseFragment(), RankContract.IRankView{

    private val mAdapter: RelativeVideoAdapter by lazy {
        RelativeVideoAdapter()
    }

    private val mRankPresenter: RankPresenter by lazy {
        RankPresenter()
    }

    private lateinit var mRefreshLayout: SmartRefreshLayout
    private lateinit var mRankRv: RecyclerView

    override val strategy: String by lazy {
        arguments?.getString(RANK_FRAGMENT_TITLE) ?: ""
    }

    init {
        mRankPresenter.attachView(this)
    }

    override fun finishRefresh() {
        mRefreshLayout.finishRefresh()
    }

    override fun setData(data: ArrayList<CommonVideoBean.ResultBean>) {
        mAdapter.setNewData(data)
    }

    override fun showLoading() {
        mRankRv.visibility = View.GONE
    }

    override fun dismissLoading() {
        mRankRv.visibility = View.VISIBLE
    }

    override fun showError(msg: String) {
        showErrorToast(msg)
        mRankRv.visibility = View.GONE
    }

    override fun showSuccess() {
        mRankRv.visibility = View.VISIBLE
    }

    override fun initView(view: View) {
        mRefreshLayout = refreshLayout
        mRankRv = rank_rv

        mRefreshLayout.setRefreshHeader(ClassicsHeader(mContext))
        mRefreshLayout.setEnableLoadMore(false)

        mRankRv.run {
            layoutManager = LinearLayoutManager(mContext)
            adapter = mAdapter
        }
        mAdapter.setFooterView(FooterView(mContext))
    }

    override fun initListener() {
        mRefreshLayout.setOnRefreshListener {
            mRankPresenter.getRankData(strategy)
        }
    }

    override fun lazyLoad() {
        mRankPresenter.getRankData(strategy)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_rank
    }

    /**
     * 伴生对象
     */
    companion object {

        const val RANK_FRAGMENT_TITLE = "strategy"

        /**
         * 返回一个fragment实例
         */
        fun  newInstance(title: String) = RankFragment().apply {
            val bundle = Bundle()
            bundle.putString(RANK_FRAGMENT_TITLE,title)
            this.arguments = bundle
        }
    }

}