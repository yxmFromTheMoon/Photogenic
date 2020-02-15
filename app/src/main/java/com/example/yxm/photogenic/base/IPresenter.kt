package com.example.yxm.photogenic.base

/**
 * Created by yxm on 2020-1-13
 * @function:Presenter基类
 */
interface IPresenter<in V: BaseView> {

    fun attachView(mRootView: V)

    fun detachView()
}