package com.example.yxm.photogenic.base

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.gyf.immersionbar.ktx.immersionBar
import com.tbruyelle.rxpermissions2.RxPermissions
import es.dmoral.toasty.Toasty

/**
 * Created by yxm on 2020-1-10
 * @function:
 */
abstract class BaseActivity: AppCompatActivity() {

    protected val rxPermission: RxPermissions by lazy {
        RxPermissions(this)
    }
    protected lateinit var mContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        mContext = this
        initDataBeforeView()
        initView()
        setStatusBarState()
        initListener()
        initData()
    }

    abstract fun getLayoutId(): Int

    abstract fun initView()

    abstract fun initData()

    abstract fun initListener()

    open fun initDataBeforeView(){}

    /**
     * 设置状态栏参数,默认透明
     */
    open fun setStatusBarState(){
        immersionBar {}
    }

    protected fun showToast(msg: String){
        Toasty.info(mContext,msg).show()
    }

    protected fun showErrorToast(msg:String){
        Toasty.error(mContext,msg).show()
    }
}