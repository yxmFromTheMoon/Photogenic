package com.example.yxm.photogenic.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.yxm.photogenic.R
import com.gyf.immersionbar.ImmersionBar
import com.gyf.immersionbar.components.SimpleImmersionFragment
import com.tbruyelle.rxpermissions2.RxPermissions
import es.dmoral.toasty.Toasty


/**
 * Created by yxm on 2020/2/2
 * @function 带沉浸式的fragment基类
 */
abstract class BaseImmersionFragment: SimpleImmersionFragment(){

    protected val TAG = this.javaClass.simpleName

    protected lateinit var mContext: Context
    /**
     * 是否第一次加载数据
     */
    private var isFirstLoad = true
    /**
     * 布局ID
     */
    protected abstract fun getLayoutId(): Int

    /**
     * 界面初始化
     */
    protected abstract fun initView(view: View)

    protected abstract fun initListener()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return if (getLayoutId() != 0) {
            inflater.inflate(getLayoutId(), container, false)
        } else {
            super.onCreateView(inflater, container, savedInstanceState)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mContext = activity as Context
        initView(view)
        initListener()
    }

    override fun initImmersionBar() {
        ImmersionBar.with(this)
                .keyboardEnable(true)
                .statusBarColor(R.color.statusBarColor)
                .init()
    }

    override fun onResume() {
        super.onResume()
        if(isFirstLoad){
            lazyLoad()
            isFirstLoad = false
        }
    }

    /**
     * 懒加载数据
     */
    abstract fun lazyLoad()

    protected fun showErrorToast(msg: String){
        Toasty.error(mContext,msg).show()
    }
}