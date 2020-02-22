package com.example.yxm.photogenic.module.home

import android.util.Log
import com.example.yxm.photogenic.base.BasePresenter
import com.example.yxm.photogenic.model.HomeModel

/**
 *Created by yxm on 2020/2/15
 *@function
 */
class HomeRecommendPresenter : BasePresenter<HomeRecommendContract.IHomeRecommendView>(), HomeRecommendContract.IHomeRecommendPresenter {

    private val mModel: HomeModel by lazy {
        HomeModel()
    }

    private var nextPageUrl: String? = null

    override fun getRecommendData() {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = mModel.getHomeRecommend()
                .subscribe({
                    it.itemList.filter { issue ->
                        issue.type == "banner2" || issue.data.text == "查看全部热门排行"
                    }.forEach { issue ->
                        it.itemList.remove(issue)
                    }
                    Log.e("HomePresenter", "${it.itemList.size}")
                    mRootView?.apply {
                        dismissLoading()
                        finishRefresh()
                        showSuccess()
                        nextPageUrl = it.nextPageUrl
                        setData(it.itemList)
                    }
                }, {
                    mRootView?.apply {
                        finishRefresh()
                        dismissLoading()
                        showError("请刷新重试")
                    }
                })
        addSubscribe(disposable)
    }

    override fun loadMoreData() {
        nextPageUrl?.let { url ->
            addSubscribe(dispose = mModel.getMoreHomeRecommend(url)
                    .subscribe({
                        nextPageUrl = it.nextPageUrl
                        it.itemList.filter { issue ->
                            issue.data.text == "猜你喜欢"
                        }.forEach { issue ->
                            it.itemList.remove(issue)
                        }
                        mRootView?.apply {
                            showSuccess()
                            setMoreData(it.itemList)
                        }
                    }, {
                        mRootView?.apply {
                            showError("${it.message}")
                        }
                    }))
        }
    }

}