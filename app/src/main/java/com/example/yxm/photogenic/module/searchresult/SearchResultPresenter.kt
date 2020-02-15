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

    /**
     * 获取搜索结果
     * @param queryWords 搜索词
     */
    override fun getSearchResult(queryWords: String) {
        checkViewAttached()
        mRootView?.showLoading()
        val dispose = mModel.getSearchData(queryWords)
                .subscribe({
                    mRootView?.apply {
                        dismissLoading()
                        if (it.itemList.isNotEmpty()) {
                            showSuccess()
                            setSearchResult(it.itemList)
                        } else {
                            mRootView?.showError("")
                        }
                        nextPageUrl = it.nextPageUrl
                    }
                }, {
                    mRootView?.showError("")
                })
        addSubscribe(dispose)
    }

    /**
     * 加载更多数据
     */
    override fun getMoreData() {
        nextPageUrl?.let { url ->
            addSubscribe(dispose = mModel.getMoreSearchData(url)
                        .subscribe({
                            mRootView?.apply {
                                if (it.itemList.isNotEmpty()) {
                                    showSuccess()
                                    loadMoreData(it.itemList)
                                }
                                nextPageUrl = it.nextPageUrl
                            }
                        }, {
                            mRootView?.loadMoreFailure()
                        }))
            }
    }
}