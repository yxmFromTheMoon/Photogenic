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

    override fun getTagVideo(id: Long) {
        mRootView?.showLoading()
        val disposable = mModel.getRelatedVideoData(id)
                .subscribe({
                    val itemList = it.itemList
                    itemList.filter {item ->
                        item.type == "textCard"
                    }.forEach {item ->
                        itemList.remove(item)
                    }
                    mRootView?.apply {
                        showSuccess()
                        dismissLoading()
                        finishRefresh()
                        setVideo(itemList)
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

}