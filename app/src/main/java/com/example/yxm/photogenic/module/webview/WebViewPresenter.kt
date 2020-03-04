package com.example.yxm.photogenic.module.webview

import com.example.yxm.photogenic.base.BasePresenter

/**
 *Created by yxm on 2020/3/4
 *@function webView Presenter
 */
class WebViewPresenter : BasePresenter<WebViewContract.IWebView>(), WebViewContract.IWebViewPresenter {

    override fun loadInfo(title: String, url: String) {
        checkViewAttached()
        mRootView?.apply {
            initWebView()
            setTitle(title)
            loadUrl(url)
        }
    }
}