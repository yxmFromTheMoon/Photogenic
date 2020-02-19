package com.example.yxm.photogenic.module.home

import com.example.yxm.photogenic.base.BasePresenter
import com.example.yxm.photogenic.model.HomeModel

/**
 *Created by yxm on 2020/2/19
 *@function 首页日报fragment Presenter
 */
class HomeReportPresenter : BasePresenter<HomeReportContract.IHomeReportView>(), HomeReportContract.IHomeReportPresenter {

    private val mModel: HomeModel by lazy {
        HomeModel()
    }
    private var nextPageUrl: String? = null

    override fun getReportData() {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = mModel.getHomeReport()
                .subscribe({
                    it.itemList.filter { issue ->
                        issue.type == "pictureFollowCard" || issue.type == "autoPlayFollowCard"
                    }.forEach { issue ->
                        it.itemList.remove(issue)
                    }
                    it.itemList.removeAt(0)
                    mRootView?.apply {
                        finishRefresh()
                        dismissLoading()
                        showSuccess()
                        nextPageUrl = it.nextPageUrl
                        setData(it.itemList)
                    }
                }, {
                    mRootView?.apply {
                        finishRefresh()
                        dismissLoading()
                        showError("获取日报失败")
                    }
                })
        addSubscribe(disposable)
    }

    override fun loadMoreData() {
        nextPageUrl?.let { url ->
            addSubscribe(dispose = mModel.getMoreHomeReport(url)
                    .subscribe({
                        mRootView?.apply {
                            dismissLoading()
                            nextPageUrl = it.nextPageUrl
                            setMoreData(it.itemList)
                        }
                    }, {
                        mRootView?.apply {
                            finishRefresh()
                            dismissLoading()
                            showError("加载更多失败")
                        }
                    }))
        }
    }
}