package com.example.yxm.photogenic.ui.activity

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.yxm.photogenic.R
import com.example.yxm.photogenic.base.BaseActivity
import com.gyf.immersionbar.ktx.immersionBar
import kotlinx.android.synthetic.main.activity_login.*

/**
 * Created by yxm on 2020-1-11
 * @function: 登录Activity
 */
class LoginActivity: BaseActivity() {
    private lateinit var login: TextView

    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }


    override fun initData() {
        Log.i("","")
    }

    override fun initView() {
        login = login_action
    }

    override fun initListener() {
        login.setOnClickListener {
            finish()
        }
    }

    override fun setStatusBarState() {
        immersionBar {
            statusBarColor("#000000")
        }
    }

}