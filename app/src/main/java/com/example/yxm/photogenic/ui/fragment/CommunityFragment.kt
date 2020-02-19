package com.example.yxm.photogenic.ui.fragment

import android.content.Intent
import android.support.v4.view.ViewPager
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import com.example.yxm.photogenic.R
import com.example.yxm.photogenic.base.BaseImmersionFragment
import com.example.yxm.photogenic.base.adapter.CommonViewPagerAdapter
import com.example.yxm.photogenic.ui.activity.SearchActivity
import com.gyf.immersionbar.ktx.immersionBar
import com.kekstudio.dachshundtablayout.DachshundTabLayout
import kotlinx.android.synthetic.main.fragment_community.*
import kotlinx.android.synthetic.main.fragment_home.header_title
import kotlinx.android.synthetic.main.fragment_home.search_iv

/**
 *Created by yxm on 2020/2/15
 *@function 社区fragment
 */
class CommunityFragment: BaseImmersionFragment() {

    private lateinit var mTitle: FrameLayout
    private lateinit var mHomeTab: DachshundTabLayout
    private lateinit var mSearchIv: ImageView
    private lateinit var mViewPager: ViewPager

    override fun initImmersionBar() {
        super.initImmersionBar()
        immersionBar {
            titleBar(mTitle)
            statusBarDarkFont(true)
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_community
    }

    override fun initView(view: View) {
        mTitle = header_title
        mHomeTab = community_tab
        mSearchIv = search_iv
        mViewPager = community_vp

        val titles = arrayOf("推荐", "关注")
        val pagerAdapter = CommonViewPagerAdapter(childFragmentManager,titles)
        val homeRecommend = CommunityRecommendFragment.newInstance()
        val homeReport = CommunityFollowFragment.newInstance()

        pagerAdapter.addFragment(homeRecommend)
        pagerAdapter.addFragment(homeReport)
        mHomeTab.setupWithViewPager(mViewPager)
        mViewPager.apply {
            adapter = pagerAdapter
            currentItem = 0
            offscreenPageLimit = titles.size
        }
    }

    override fun initListener() {
        mSearchIv.setOnClickListener {
            startActivity(Intent(mContext, SearchActivity::class.java))
        }
    }

    override fun lazyLoad() {
    }

    companion object{
        fun newInstance(): CommunityFragment = CommunityFragment()
    }
}