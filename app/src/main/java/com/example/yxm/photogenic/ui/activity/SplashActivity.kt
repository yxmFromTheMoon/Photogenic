package com.example.yxm.photogenic.ui.activity

import android.os.Bundle
import android.widget.ImageView
import com.example.yxm.photogenic.base.BaseActivity
import com.example.yxm.photogenic.R
import com.gyf.immersionbar.ktx.hideStatusBar
import com.gyf.immersionbar.ktx.immersionBar
import kotlinx.android.synthetic.main.activity_splash.*


/**
 * Created by yxm on 2020-1-10
 * @function:闪屏页
 */
class SplashActivity: BaseActivity() {

    private lateinit var splashImageView: ImageView

    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }

    override fun initData() {
        /**
         * NoData
         */
    }

    override fun initListener() {
        /**
         * NoListener
         */
    }

    override fun initView() {
        splashImageView = splash_iv
    }

    override fun setStatusBarState() {
        immersionBar {
            hideStatusBar()
        }
    }
}