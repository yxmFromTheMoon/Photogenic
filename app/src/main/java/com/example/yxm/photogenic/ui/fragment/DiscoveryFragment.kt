package com.example.yxm.photogenic.ui.fragment

import android.view.View
import com.example.yxm.photogenic.R
import com.example.yxm.photogenic.base.BaseFragment

/**
 * Created by yxm on 2020-1-14
 * @function:发现fragment
 */
class DiscoveryFragment: BaseFragment() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_discovery
    }

    /**
     * 初始化view
     */
    override fun initView(view: View) {
        /**
         * TODO
         */
    }

    override fun initListener() {

    }

    /**
     * 懒加载数据
     */
    override fun lazyLoad() {
        /**
         * TODO
         */
    }

    /**
     * 伴生对象
     */
    companion object {
        /**
         * 返回一个fragment实例
         */
        fun newInstance(): DiscoveryFragment{
            return DiscoveryFragment()
        }
    }
}