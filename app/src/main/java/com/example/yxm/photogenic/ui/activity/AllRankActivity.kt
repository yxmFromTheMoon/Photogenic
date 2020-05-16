package com.example.yxm.photogenic.ui.activity

import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.yxm.photogenic.R
import com.example.yxm.photogenic.base.BaseActivity
import com.example.yxm.photogenic.base.BaseFragment
import com.example.yxm.photogenic.base.adapter.CommonViewpager2Adapter
import com.example.yxm.photogenic.ui.fragment.RankFragment
import com.example.yxm.photogenic.widget.LeftBackTitle
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.gyf.immersionbar.ktx.immersionBar
import kotlinx.android.synthetic.main.activity_all_rank.*

/**
 *Created by yxm on 2020/2/13
 *@function 全部排行activity
 */
class AllRankActivity : BaseActivity() {

    private lateinit var mTabLayout: TabLayout
    private lateinit var mViewPager: ViewPager2
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
        val fragments = ArrayList<BaseFragment>()
        fragments.add(RankFragment.newInstance(keys[0]))
        fragments.add(RankFragment.newInstance(keys[1]))
        fragments.add(RankFragment.newInstance(keys[2]))
        val pagerAdapter = CommonViewpager2Adapter(mContext as FragmentActivity, fragments)
        mViewPager.adapter = pagerAdapter
        TabLayoutMediator(mTabLayout, mViewPager, TabLayoutMediator.TabConfigurationStrategy { tab, position ->
            tab.text = titles[position]
        }).attach()
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