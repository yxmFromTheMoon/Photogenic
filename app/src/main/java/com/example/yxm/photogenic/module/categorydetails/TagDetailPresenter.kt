package com.example.yxm.photogenic.module.categorydetails

import com.example.yxm.photogenic.base.BasePresenter
import com.example.yxm.photogenic.model.VideoTypeModel

/**
 *Created by yxm on 2020/2/13
 *@function
 */
class TagDetailPresenter : BasePresenter<TagDetailContract.ITagDetailView>(), TagDetailContract.ITagDetailPresenter {

    private val mModel: VideoTypeModel by lazy {
        VideoTypeModel()
    }
    private var nextPageUrl: String? = null

    override fun getTagsVideo(id: Long) {
        mRootView?.showLoading()
        val disposable = mModel.getTagVideoData(id)
                .subscribe({
                    val itemList = it.itemList
                    mRootView?.apply {
                        showSuccess()
                        dismissLoading()
                        finishRefresh()
                        nextPageUrl = it.nextPageUrl
                        setTagVideo(itemList)
                    }
                }, {
                    mRootView?.apply {
                        dismissLoading()
                        finishRefresh()
                        showError("该标签下暂无数据")
                    }
                })
        addSubscribe(disposable)
    }

    override fun loadMoreVideo() {
        nextPageUrl?.let { url ->
            addSubscribe(dispose = mModel.loadMoreTagVideoData(url)
                    .subscribe({
                        val itemList = it.itemList
                        mRootView?.apply {
                            nextPageUrl = it.nextPageUrl
                            loadMoreVideo(itemList)
                        }
                    }, {
                        mRootView?.apply {
                            showError("加载更多失败")
                        }
                    }))
        }
    }
}