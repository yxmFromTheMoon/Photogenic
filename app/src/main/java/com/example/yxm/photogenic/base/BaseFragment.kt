package com.example.yxm.photogenic.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tbruyelle.rxpermissions2.RxPermissions
import es.dmoral.toasty.Toasty

/**
 * Created by yxm on 2020-1-13
 * @function: fragment基类,懒加载
 */
abstract class BaseFragment: Fragment() {

    protected val rxPermission: RxPermissions by lazy {
        RxPermissions(this)
    }
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

    /**
     * androidx使用此方式便可实现懒加载
     */
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