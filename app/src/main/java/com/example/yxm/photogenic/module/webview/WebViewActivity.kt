package com.example.yxm.photogenic.module.webview

import android.content.Context
import android.content.Intent
import android.support.design.widget.AppBarLayout
import android.support.v7.widget.Toolbar
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import android.widget.TextView
import com.example.yxm.photogenic.R
import com.example.yxm.photogenic.base.BaseActivity
import com.gyf.immersionbar.ktx.immersionBar
import kotlinx.android.synthetic.main.activity_web_view.*

/**
 *Created by yxm on 2020/3/4
 *@function webView Activity
 */
class WebViewActivity : BaseActivity(), WebViewContract.IWebView {

    lateinit var mWebTitle: TextView
    lateinit var mWebToolbar: Toolbar
    lateinit var mWebProgressBar: ProgressBar
    lateinit var mWebAppbar: AppBarLayout
    lateinit var mWebView: WebView

    private val mPresenter: WebViewPresenter by lazy {
        WebViewPresenter()
    }

    init {
        mPresenter.attachView(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_web_view
    }

    override fun setStatusBarState() {
        super.setStatusBarState()
        immersionBar {
            titleBar(web_toolbar)
            statusBarDarkFont(true)
            statusBarColor(R.color.statusBarColor)
        }
    }

    override fun initView() {
        mWebTitle = web_title
        mWebAppbar = web_appbar
        mWebProgressBar = web_progressBar
        mWebToolbar = web_toolbar
        mWebView = web_view
        setSupportActionBar(mWebToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun initData() {
        val title = intent.getStringExtra(TITLE)
        val url = intent.getStringExtra(URL)
        mPresenter.loadInfo(title, url)
    }

    override fun initListener() {
        mWebToolbar.setNavigationOnClickListener {
            finish()
        }
    }

    override fun initWebView() {
        val settings = mWebView.settings
        settings.loadWithOverviewMode = true
        settings.javaScriptEnabled = true
        settings.setAppCacheEnabled(true)
        settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        settings.setSupportZoom(true)
        mWebView.webChromeClient = MyWebChrome()
        mWebView.webViewClient = MyWebClient()
    }

    private inner class MyWebChrome : WebChromeClient() {
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            mWebProgressBar.visibility = View.VISIBLE
            mWebProgressBar.progress = newProgress
        }
    }

    private inner class MyWebClient : WebViewClient() {
        override fun onPageFinished(view: WebView?, url: String?) {
            mWebProgressBar.visibility = View.GONE
        }
    }

    override fun onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack()
        } else {
            finish()
        }
    }

    override fun setTitle(title: String) {
        mWebTitle.text = title
    }

    override fun loadUrl(url: String) {
        mWebView.loadUrl(url)
    }

    override fun showLoading() {
    }

    override fun dismissLoading() {
    }

    override fun showError(msg: String) {
    }

    override fun showSuccess() {
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

    companion object {
        const val TITLE = "title"
        const val URL = "url"
        fun start(context: Context, title: String, url: String) {
            context.startActivity(Intent(context, WebViewActivity::class.java).apply {
                putExtra(TITLE, title)
                putExtra(URL, url)
            })
        }
    }
}