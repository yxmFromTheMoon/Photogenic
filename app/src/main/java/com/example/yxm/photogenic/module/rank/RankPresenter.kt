package com.example.yxm.photogenic.module.rank

import com.example.yxm.photogenic.base.BasePresenter
import com.example.yxm.photogenic.model.RankModel

/**
 *Created by yxm on 2020/2/15
 *@function 排行presenter
 */
class RankPresenter: BasePresenter<RankContract.IRankView>(),RankContract.IRankPresenter {

    private val mModel: RankModel by lazy {
        RankModel()
    }

    override fun getRankData(strategy: String) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = mModel.getRankData(strategy)
                .subscribe({
                    mRootView?.apply {
                        finishRefresh()
                        dismissLoading()
                        showSuccess()
                        setData(it.itemList)
                    }
                },{
                    mRootView?.apply {
                        finishRefresh()
                        dismissLoading()
                        showError("获取排行失败")
                    }
                })

        addSubscribe(disposable)

    }
}