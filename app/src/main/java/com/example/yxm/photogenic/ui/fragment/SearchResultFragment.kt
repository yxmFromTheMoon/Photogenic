package com.example.yxm.photogenic.ui.fragment

import android.os.Bundle
import android.view.View
import com.example.lib_network.bean.CommonVideoBean
import com.example.yxm.photogenic.R
import com.example.yxm.photogenic.base.BaseActivity
import com.example.yxm.photogenic.base.BaseFragment
import com.example.yxm.photogenic.model.SearchResultModel
import com.example.yxm.photogenic.module.searchresult.SearchResultContract

/**
 * Created by yxm on 2020-1-16
 * @function:搜索结果fragment
 */
class SearchResultFragment: BaseFragment(),SearchResultContract.ISearchResultView {

    private val mModel: SearchResultModel by lazy {
        SearchResultModel()
    }

    override val queryWords: String by lazy {
        arguments?.getString(SearchResultFragment.QUERY_WORDS) ?: ""
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_search_result
    }

    override fun initView(view: View) {
    }

    override fun initListener() {

    }

    override fun lazyLoad() {
        mModel.getSearchData(queryWords)
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