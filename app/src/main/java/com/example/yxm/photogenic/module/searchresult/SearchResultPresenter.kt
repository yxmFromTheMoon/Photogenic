package com.example.yxm.photogenic.module.searchresult

import com.example.yxm.photogenic.base.BasePresenter
import com.example.yxm.photogenic.model.SearchResultModel

/**
 * Created by yxm on 2020-1-16
 * @function: 搜索结果
 */
class SearchResultPresenter : BasePresenter<SearchResultContract.ISearchResultView>(), SearchResultContract.ISearchResultPresenter {

    private var nextPageUrl: String? = null
    private val mModel: SearchResultModel by lazy {
        SearchResultModel()
    }

    override fun getSearchResult(queryWords: String) {
        mRootView?.showLoading()
        val dispose = mModel.getSearchData(queryWords)
                .subscribe({
                    mRootView?.apply {
                        dismissLoading()
                        if (it.itemList.isNotEmpty()) {
                            setSearchResult(it.itemList)
                        } else {
                            mRootView?.showEmptyView()
                        }
                        nextPageUrl = it.nextPageUrl
                    }
                }, {
                    mRootView?.showEmptyView()
                })
        addSubscribe(dispose)
    }

    override fun getMoreData() {
        val dispose = nextPageUrl?.let { url ->
            mModel.getMoreSearchData(url)
                    .subscribe({
                        mRootView?.apply {
                            setSearchResult(it.itemList)
                            mRootView?.showEmptyView()
                            nextPageUrl = it.nextPageUrl
                        }
                    }, {
                        mRootView?.showEmptyView()
                    })
        }
        dispose?.let {
            addSubscribe(dispose)
        }
    }
}