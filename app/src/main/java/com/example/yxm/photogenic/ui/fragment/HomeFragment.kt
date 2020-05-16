package com.example.yxm.photogenic.ui.fragment

import android.content.Intent
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.yxm.photogenic.R
import com.example.yxm.photogenic.base.BaseFragment
import com.example.yxm.photogenic.base.BaseImmersionFragment
import com.example.yxm.photogenic.base.adapter.CommonViewpager2Adapter
import com.example.yxm.photogenic.ui.activity.SearchActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.gyf.immersionbar.ktx.immersionBar
import kotlinx.android.synthetic.main.fragment_home.*

/**
 *Created by yxm on 2020/2/15
 *@function 首页fragment
 */
class HomeFragment : BaseImmersionFragment() {

    private lateinit var mTitle: FrameLayout
    private lateinit var mHomeTab: TabLayout
    private lateinit var mSearchIv: ImageView
    private lateinit var mViewPager: ViewPager2

    override fun initImmersionBar() {
        super.initImmersionBar()
        immersionBar {
            titleBar(mTitle)
            statusBarDarkFont(true)
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun initView(view: View) {
        mTitle = header_title
        mHomeTab = home_tab
        mSearchIv = search_iv
        mViewPager = home_vp

        val titles = arrayOf("推荐", "日报")
        val fragments = ArrayList<BaseFragment>()
        fragments.add(HomePageRecommendFragment.newInstance())
        fragments.add(HomePageDailyReportFragment.newInstance())
        val pager2Adapter = CommonViewpager2Adapter(mContext as FragmentActivity, fragments)

        mViewPager.adapter = pager2Adapter

        TabLayoutMediator(mHomeTab, mViewPager, TabLayoutMediator.TabConfigurationStrategy { tab, position ->
            tab.text = (titles[position])
        }).attach()
    }

    override fun initListener() {
        mSearchIv.setOnClickListener {
            startActivity(Intent(mContext, SearchActivity::class.java))
        }
    }

    override fun lazyLoad() {
    }

    companion object {
        fun newInstance(): HomeFragment = HomeFragment()
    }
}