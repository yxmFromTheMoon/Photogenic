package com.example.yxm.photogenic.module.webview

import com.example.yxm.photogenic.base.BaseView
import com.example.yxm.photogenic.base.IPresenter

/**
 *Created by yxm on 2020/3/4
 *@function webView Contract
 */
interface WebViewContract {

    interface IWebView : BaseView {

        fun initWebView()

        fun setTitle(title: String)

        fun loadUrl(url: String)
    }

    interface IWebViewPresenter : IPresenter<IWebView> {
        fun loadInfo(title: String,url: String)
    }
}