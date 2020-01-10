package com.example.yxm.photogenic

import android.os.Bundle
import com.example.yxm.photogenic.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent
import com.gyf.immersionbar.ktx.immersionBar


/**
 * Created by yxm on 2020-1-10
 * @function:
 */
class SplashActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val hello = hello
        immersionBar {
            statusBarDarkFont(true)
        }
        hello.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }
    }
}