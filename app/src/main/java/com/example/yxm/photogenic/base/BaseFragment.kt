package com.example.yxm.photogenic.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tbruyelle.rxpermissions2.RxPermissions

/**
 * Created by yxm on 2020-1-13
 * @function: fragment基类,懒加载
 */
abstract class BaseFragment: Fragment() {

    protected val rxPermission: RxPermissions by lazy {
        RxPermissions(this)
    }
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return if (getLayoutId() != 0) {
            inflater.inflate(getLayoutId(), container, false)
        } else {
            super.onCreateView(inflater, container, savedInstanceState)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isViewPrepare = true
        initView(view)
        lazyLoadDataIfPrepared()
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

    override fun onDestroyView() {
        super.onDestroyView()
    }
}