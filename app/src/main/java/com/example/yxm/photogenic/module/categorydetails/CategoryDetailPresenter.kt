package com.example.yxm.photogenic.module.categorydetails

import com.example.yxm.photogenic.base.BasePresenter
import com.example.yxm.photogenic.model.VideoTypeModel

/**
 *Created by yxm on 2020/2/12
 *@function 分类详情presenter
 */
class CategoryDetailPresenter : BasePresenter<CategoryDetailContract.ICategoryDetailView>(), CategoryDetailContract.ICategoryDetailPresenter {

    private val mModel: VideoTypeModel by lazy {
        VideoTypeModel()
    }

    override fun getVideo(id: Int) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = mModel.getCategoryDetailVideo(id)
                .subscribe({
                    mRootView?.apply {
                        dismissLoading()
                        finishRefresh()
                        setVideo(it.result)
                    }
                }, {
                    mRootView?.apply {
                        finishRefresh()
                        dismissLoading()
                        showError("视频获取失败")
                    }
                })
        addSubscribe(disposable)
    }

    /**
     * 暂无实现
     */
    override fun loadMoreVideo(id: Long) {
        mRootView?.showLoading()

        val disposable = mModel.getRelatedVideoData(id)
                .subscribe({

                }, {
                    mRootView?.apply {
                        dismissLoading()
                        showError("获取更多失败")
                    }
                })
        addSubscribe(disposable)
    }

}