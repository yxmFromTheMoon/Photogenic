package com.example.yxm.photogenic.module.searchresult

import com.example.lib_network.bean.CommonVideoBean
import com.example.yxm.photogenic.base.BaseView
import com.example.yxm.photogenic.base.IPresenter

/**
 * Created by yxm on 2020-1-16
 * @function:搜索结果
 */
interface SearchResultContract {
    interface ISearchResultView: BaseView{
        val queryWords: String

        fun setSearchResult(data: ArrayList<CommonVideoBean.ResultBean>)

        fun loadMoreData(data: ArrayList<CommonVideoBean.ResultBean>)

        fun showEmptyView()

        fun loadMoreFailure()

        fun showSearchView()
    }

    interface ISearchResultPresenter: IPresenter<ISearchResultView>{

        fun getSearchResult(queryWords: String)

        fun getMoreData()
    }
}