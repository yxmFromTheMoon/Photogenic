package com.example.yxm.photogenic.ui.activity

import android.support.v4.view.ViewPager
import com.example.yxm.photogenic.R
import com.example.yxm.photogenic.base.BaseActivity
import com.example.yxm.photogenic.base.adapter.CommonViewPagerAdapter
import com.example.yxm.photogenic.ui.fragment.RankFragment
import com.example.yxm.photogenic.widget.LeftBackTitle
import com.gyf.immersionbar.ktx.immersionBar
import com.kekstudio.dachshundtablayout.DachshundTabLayout
import kotlinx.android.synthetic.main.activity_all_rank.*

/**
 *Created by yxm on 2020/2/13
 *@function 全部排行activity
 */
class AllRankActivity : BaseActivity() {

    private lateinit var mTabLayout: DachshundTabLayout
    private lateinit var mViewPager: ViewPager
    private lateinit var mTitle: LeftBackTitle

    override fun getLayoutId(): Int {
        return R.layout.activity_all_rank
    }

    override fun initView() {
        mTabLayout = rank_tab
        mViewPager = rank_vp
        mTitle = header_title
        mTitle.setLeftFinish(this)
        val keys = arrayOf("weekly", "monthly", "historical")
        val titles = arrayOf("周排行", "月排行", "总排行")
        val pagerAdapter = CommonViewPagerAdapter(supportFragmentManager,titles)
        val weekFragment = RankFragment.newInstance(keys[0])
        val monthFragment = RankFragment.newInstance(keys[1])
        val totalFragment = RankFragment.newInstance(keys[2])
        pagerAdapter.addFragment(weekFragment)
        pagerAdapter.addFragment(monthFragment)
        pagerAdapter.addFragment(totalFragment)

        mViewPager.adapter = pagerAdapter
        mTabLayout.setupWithViewPager(mViewPager)
        mViewPager.currentItem = 0
        mViewPager.offscreenPageLimit = titles.size

    }

    override fun initData() {
    }

    override fun initListener() {
    }

    override fun setStatusBarState() {
        immersionBar {
            titleBar(mTitle)
            statusBarDarkFont(true)
        }
    }
}