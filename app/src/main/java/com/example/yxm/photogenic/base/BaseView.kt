package com.example.yxm.photogenic.base

/**
 * Created by yxm on 2020-1-13
 * @function: baseview
 */
interface BaseView {
    fun showLoading()

    fun dismissLoading()

    fun showError(msg: String)

    fun showSuccess()
}