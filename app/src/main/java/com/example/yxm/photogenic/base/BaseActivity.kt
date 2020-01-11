package com.example.yxm.photogenic.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.gyf.immersionbar.ImmersionBar
import com.gyf.immersionbar.ktx.immersionBar
import es.dmoral.toasty.Toasty

/**
 * Created by yxm on 2020-1-10
 * @function:
 */
abstract class BaseActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        setStatusBarState()
        initView()
        initListener()
        initData()
    }

    abstract fun getLayoutId(): Int

    abstract fun initView()

    abstract fun initData()

    abstract fun initListener()

    /**
     * 设置状态栏参数,默认透明
     */
    open fun setStatusBarState(){
        immersionBar {}
    }

    protected fun showToast(msg: String){
        Toasty.info(this,msg).show()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}