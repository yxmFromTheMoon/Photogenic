package com.example.yxm.photogenic.module.community

import com.example.lib_network.bean.CommonVideoBean
import com.example.yxm.photogenic.base.BasePresenter
import com.example.yxm.photogenic.model.CommunityFollowModel

/**
 *Created by yxm on 2020/2/22
 *@function 社区关注presenter
 */
class CommunityFollowPresenter : BasePresenter<CommunityFollowContract.ICommunityFollowView>(), CommunityFollowContract.ICommunityFollowPresenter {

    private var nextPageUrl: String? = null

    private val mModel: CommunityFollowModel by lazy {
        CommunityFollowModel()
    }

    override fun getFollowData() {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = mModel.getCommunityFollowData()
                .subscribe({
                    val resultList = ArrayList<CommonVideoBean.ResultBean>()
                    for (data in it.itemList){
                        for (item in data.data.itemList){
                            resultList.add(item)
                        }
                    }
                    nextPageUrl = it.nextPageUrl
                    mRootView?.apply {
                        dismissLoading()
                        finishRefresh()
                        showSuccess()
                        setData(resultList)
                    }
                }, {
                    mRootView?.apply {
                        dismissLoading()
                        finishRefresh()
                        showError("获取数据失败${it.message}")
                    }
                })
        addSubscribe(disposable)
    }

    override fun loadMoreData() {
        nextPageUrl?.let { url ->
            addSubscribe(dispose = mModel.getMoreCommunityFollowData(url)
                    .subscribe({
                        val resultList = ArrayList<CommonVideoBean.ResultBean>()
                        for (data in it.itemList){
                            for (item in data.data.itemList){
                                resultList.add(item)
                            }
                        }
                        nextPageUrl = it.nextPageUrl
                        mRootView?.apply {
                            setMoreData(resultList)
                        }
                    }, {
                        mRootView?.apply {
                            showError("获取更多数据失败")
                        }
                    }))
        }
    }
}