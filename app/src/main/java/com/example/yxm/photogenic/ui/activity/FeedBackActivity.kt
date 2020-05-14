package com.example.yxm.photogenic.ui.activity

import androidx.appcompat.widget.Toolbar
import com.example.yxm.photogenic.R
import com.example.yxm.photogenic.base.BaseActivity
import com.gyf.immersionbar.ktx.immersionBar
import kotlinx.android.synthetic.main.activity_feed_back.*

/**
 *Created by yxm on 2020/3/4
 *@function 问题反馈界面
 */
class FeedBackActivity : BaseActivity() {

    private lateinit var mToolBar: Toolbar

    override fun getLayoutId(): Int {
        return R.layout.activity_feed_back
    }

    override fun initView() {
        mToolBar = feed_toolbar
        setSupportActionBar(mToolBar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        immersionBar {
            titleBar(mToolBar)
            statusBarDarkFont(true)
            statusBarColor(R.color.statusBarColor)
        }
    }

    override fun initData() {
    }

    override fun initListener() {
        mToolBar.setNavigationOnClickListener { finish() }
    }
}