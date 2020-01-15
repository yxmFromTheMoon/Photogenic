package com.example.yxm.photogenic.ui.fragment

import android.os.Bundle
import android.view.View
import com.example.yxm.photogenic.R
import com.example.yxm.photogenic.base.BaseFragment
import com.example.yxm.photogenic.module.rank.RankContract

/**
 * Created by yxm on 2020-1-15
 * @function:
 */
class RankFragment: BaseFragment(), RankContract.IRankView{

    override val title: String by lazy {
        arguments?.getString(RankFragment.RANK_FRAGMENT_TITLE) ?: ""
    }

    override fun showLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun dismissLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initView(view: View) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun lazyLoad() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initImmersionBar() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_rank
    }

    /**
     * 伴生对象
     */
    companion object {

        const val RANK_FRAGMENT_TITLE = "title"

        /**
         * 返回一个fragment实例
         */
        fun newInstance(title: String): RankFragment{
            val rankFragment = RankFragment()
            val bundle = Bundle()
            bundle.putString(RANK_FRAGMENT_TITLE,title)
            rankFragment.arguments = bundle
            return rankFragment
        }
    }

}