package com.example.yxm.photogenic.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.lib_network.bean.CommonVideoBean
import com.example.lib_share.share.ShareManager
import com.example.yxm.photogenic.R
import com.example.yxm.photogenic.base.BaseFragment
import com.example.yxm.photogenic.font.FontTextView
import com.example.yxm.photogenic.module.searchresult.SearchResultAdapter
import com.example.yxm.photogenic.module.searchresult.SearchResultContract
import com.example.yxm.photogenic.module.searchresult.SearchResultPresenter
import com.example.yxm.photogenic.ui.activity.VideoPlayActivity
import com.example.yxm.photogenic.widget.FooterView
import kotlinx.android.synthetic.main.fragment_search_result.*

/**
 * Created by yxm on 2020-1-16
 * @function:搜索结果fragment
 */
class SearchResultFragment : BaseFragment(), SearchResultContract.ISearchResultView {

    private lateinit var searchResultRv: RecyclerView
    private lateinit var emptyView: FontTextView
    private var loadMore = false

    private val searchResultPresenter: SearchResultPresenter by lazy {
        SearchResultPresenter()
    }

    private val mAdapter: SearchResultAdapter by lazy {
        SearchResultAdapter()
    }

    override val queryWords: String by lazy {
        arguments?.getString(QUERY_WORDS) ?: ""
    }

    init {
        searchResultPresenter.attachView(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_search_result
    }

    override fun initView(view: View) {
        searchResultRv = search_result_rv
        emptyView = empty_tv
        mAdapter.setFooterView(FooterView(mContext))
        searchResultRv.run {
            layoutManager = LinearLayoutManager(mContext)
            adapter = mAdapter
        }
    }

    override fun initListener() {
        //自动加载更多
        searchResultRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val itemCount = searchResultRv.layoutManager!!.itemCount
                val lastVisibleItem = (searchResultRv.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                if (!loadMore && (lastVisibleItem == (itemCount - 1))) {
                    loadMore = true
                    searchResultPresenter.getMoreData()
                }
            }
        })

        mAdapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
            val bean = adapter.getItem(position) as CommonVideoBean.ResultBean
            startActivity(Intent(mContext, VideoPlayActivity::class.java).apply {
                val bundle = Bundle()
                bundle.putSerializable("video", bean)
                bundle.putLong("relativeVideoId", bean.data.id)
                bundle.putInt("fromWhere", SEARCH_RESULT)
                putExtras(bundle)
            })
        }

        //分享
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
    }

    override fun lazyLoad() {
        searchResultPresenter.getSearchResult(queryWords)
    }

    /**
     * 初始化数据
     */
    override fun setSearchResult(data: ArrayList<CommonVideoBean.ResultBean>) {
        mAdapter.setNewData(data)
    }

    /**
     * 加载更多数据
     */
    override fun loadMoreData(data: ArrayList<CommonVideoBean.ResultBean>) {
        loadMore = false
        mAdapter.addData(data)
    }

    override fun loadMoreFailure() {
        showErrorToast("加载更多失败")
    }

    override fun showLoading() {

    }

    override fun dismissLoading() {

    }

    override fun showError(msg: String) {
        emptyView.visibility = VISIBLE
        searchResultRv.visibility = GONE
    }

    override fun showSuccess() {
        emptyView.visibility = GONE
        searchResultRv.visibility = VISIBLE
    }

    companion object {

        const val QUERY_WORDS = "queryWords"
        const val SEARCH_RESULT = 7
        /**
         * 返回一个fragment实例
         */
        fun newInstance(queryWords: String?) = SearchResultFragment().apply {
            val bundle = Bundle()
            bundle.putString(QUERY_WORDS, queryWords)
            this.arguments = bundle
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        searchResultPresenter.detachView()
    }
}