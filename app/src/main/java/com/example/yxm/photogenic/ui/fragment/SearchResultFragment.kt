package com.example.yxm.photogenic.ui.fragment

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.lib_network.bean.CommonVideoBean
import com.example.yxm.photogenic.R
import com.example.yxm.photogenic.base.BaseFragment
import com.example.yxm.photogenic.font.FontTextView
import com.example.yxm.photogenic.module.searchresult.SearchResultContract
import com.example.yxm.photogenic.module.searchresult.SearchResultPresenter
import kotlinx.android.synthetic.main.fragment_search_result.*

/**
 * Created by yxm on 2020-1-16
 * @function:搜索结果fragment
 */
class SearchResultFragment: BaseFragment(),SearchResultContract.ISearchResultView {

    private lateinit var searResultRv: RecyclerView
    private lateinit var emptyView: FontTextView

    private val searchResultPresenter: SearchResultPresenter by lazy {
        SearchResultPresenter()
    }

    override val queryWords: String by lazy {
        arguments?.getString(SearchResultFragment.QUERY_WORDS) ?: ""
    }

    init {
        searchResultPresenter.attachView(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_search_result
    }

    override fun initView(view: View) {
        searResultRv = search_result_rv
        emptyView = empty_tv
    }

    override fun initListener() {

    }

    override fun lazyLoad() {
        searchResultPresenter.getSearchResult(queryWords)
    }

    override fun setSearchResult(data: ArrayList<CommonVideoBean.ResultBean>) {

    }

    override fun showEmptyView() {

    }

    override fun showLoading() {

    }

    override fun dismissLoading() {

    }

    override fun initImmersionBar() {
    }

    companion object {

        const val QUERY_WORDS = "queryWords"
        /**
         * 返回一个fragment实例
         */
        fun  newInstance(queryWords: String) = SearchResultFragment().apply {
            val bundle = Bundle()
            bundle.putString(QUERY_WORDS,queryWords)
            this.arguments = bundle
        }
    }

}