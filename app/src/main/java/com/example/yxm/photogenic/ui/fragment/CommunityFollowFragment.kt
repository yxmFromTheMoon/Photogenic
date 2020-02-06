package com.example.yxm.photogenic.ui.fragment

import android.view.View
import com.example.yxm.photogenic.R
import com.example.yxm.photogenic.base.BaseFragment
import com.example.yxm.photogenic.base.BaseImmersionFragment

/**
 * Created by yxm on 2020-1-14
 * @function:社区关注fragment
 */
class CommunityFollowFragment: BaseImmersionFragment() {

    override fun initImmersionBar() {

    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_community_follow
    }

    override fun initView(view: View) {

    }

    override fun initListener() {

    }

    override fun lazyLoad() {

    }

    /**
     * 伴生对象
     */
    companion object {
        /**
         * 返回一个fragment实例
         */
        fun newInstance(): CommunityFollowFragment{
            return CommunityFollowFragment()
        }
    }

}