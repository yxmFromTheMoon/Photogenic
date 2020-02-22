package com.example.yxm.photogenic.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by yxm on 2020-1-13
 * @function: basePresenter
 */
open class BasePresenter<T : BaseView> : IPresenter<T> {
    private var compositeDisposable = CompositeDisposable()
    var mRootView: T? = null
        private set

    private val isViewAttached: Boolean
        get() = mRootView != null

    fun checkViewAttached() {
        if (!isViewAttached) throw MvpViewNotAttachedException()
    }

    fun addSubscribe(dispose: Disposable) {
        compositeDisposable.add(dispose)
    }

    override fun attachView(mRootView: T) {
        this.mRootView = mRootView
    }

    override fun detachView() {
        mRootView = null
        //保证activity结束时取消所有正在执行的订阅
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.clear()
        }
    }

    private class MvpViewNotAttachedException internal constructor() :
            RuntimeException("Please call IPresenter.attachView(IBaseView) before" + " requesting data to the IPresenter")
}
