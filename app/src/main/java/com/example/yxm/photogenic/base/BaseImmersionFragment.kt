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

    protected val rxPermission: RxPermissions by lazy {
        RxPermissions(this)
    }

    protected lateinit var mContext: Context
    /**
     * 视图是否加载完毕
     */
    private var isViewPrepare = false
    /**
     * 数据是否加载过了
     */
    private var hasLoadData = false
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
        isViewPrepare = true
        initView(view)
        initListener()
        lazyLoadDataIfPrepared()
    }

    override fun initImmersionBar() {
        ImmersionBar.with(this)
                .keyboardEnable(true)
                .statusBarColor(R.color.statusBarColor)
                .init()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if(isVisibleToUser){
            lazyLoadDataIfPrepared()
        }
    }

    /**
     * view准备完毕并且没有加载过数据
     */
    private fun lazyLoadDataIfPrepared(){
        if(userVisibleHint && isViewPrepare && !hasLoadData){
            lazyLoad()
            hasLoadData = true
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