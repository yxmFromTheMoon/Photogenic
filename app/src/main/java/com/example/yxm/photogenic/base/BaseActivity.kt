package com.example.yxm.photogenic.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.gyf.immersionbar.ktx.immersionBar

/**
 * Created by yxm on 2020-1-10
 * @function:
 */
open class BaseActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        immersionBar{}
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}