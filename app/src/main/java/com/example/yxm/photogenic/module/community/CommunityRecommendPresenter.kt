package com.example.yxm.photogenic.module.community

import com.example.yxm.photogenic.base.BasePresenter
import com.example.yxm.photogenic.model.CommunityRecommendModel

/**
 *Created by yxm on 2020/2/20
 *@function 获取社区推荐数据
 */
class CommunityRecommendPresenter : BasePresenter<CommunityRecommendContract.ICommunityRecommendView>(),
        CommunityRecommendContract.ICommunityRecommendPresenter {

    private val mModel: CommunityRecommendModel by lazy {
        CommunityRecommendModel()
    }
    private var nextPageUrl: String? = null

    /**
     * 初始化数据
     */
    override fun getRecommendData() {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = mModel.getCommunityRecommendData()
                .subscribe({
                    it.itemList.filter { issue ->
                        issue.type == "squareCardCollection"
                    }.forEach { issue ->
                        it.itemList.remove(issue)
                    }
                    mRootView?.apply {
                        nextPageUrl = it.nextPageUrl
                        dismissLoading()
                        finishRefresh()
                        showSuccess()
                        setData(it.itemList)
                    }
                }, {
                    mRootView?.apply {
                        dismissLoading()
                        finishRefresh()
                        showError("获取社区推荐失败${it.message}")
                    }
                })
        addSubscribe(disposable)
    }

    /**
     * 加载更多
     */
    override fun loadMoreData() {
        nextPageUrl?.let { url ->
            addSubscribe(dispose = mModel.getMoreCommunityRecommendData(url)
                    .subscribe({
                        mRootView?.apply {
                            nextPageUrl = it.nextPageUrl
                            setMoreData(it.itemList)
                        }
                    }, {
                        mRootView?.apply {
                            dismissLoading()
                            showError("获取更多社区推荐失败${it.message}")
                        }
                    }))
        }
    }
}