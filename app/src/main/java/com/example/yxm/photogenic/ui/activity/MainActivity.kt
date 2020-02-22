package com.example.yxm.photogenic.ui.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.view.KeyEvent
import android.widget.FrameLayout
import com.example.lib_share.share.ShareDialog
import com.example.yxm.photogenic.R
import com.example.yxm.photogenic.base.BaseActivity
import com.example.yxm.photogenic.ui.fragment.*
import com.example.yxm.photogenic.utils.KeyBoardHelper
import com.gyf.immersionbar.ktx.immersionBar
import com.gyf.immersionbar.ktx.showStatusBar
import com.yinglan.alphatabs.AlphaTabView
import com.yinglan.alphatabs.AlphaTabsIndicator
import kotlinx.android.synthetic.main.activity_main.*

/**
 * MainActivity
 */
class MainActivity : BaseActivity() {

    private lateinit var contentLayout: FrameLayout
    private lateinit var homePageTab: AlphaTabView
    private lateinit var communityTab: AlphaTabView
    private lateinit var discoveryTab: AlphaTabView
    private lateinit var mineTab: AlphaTabView
    private lateinit var mAlphaIndicator: AlphaTabsIndicator
    private var mExitTime: Long = 0
    private var mSplashFragment: SplashFragment? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
    }

    override fun setStatusBarState() {

    }

    override fun initView() {
        contentLayout = content_layout
        homePageTab = home_page_tab
        communityTab = community_tab
        discoveryTab = discovery_tab
        mineTab = mine_tab
        mAlphaIndicator = alphaIndicator
        showSplash()
        initFragment(R.id.home_page_tab)
    }

    override fun initData() {

    }

    override fun initListener() {
        mAlphaIndicator.setOnTabChangedListner {
            initFragment(mAlphaIndicator.getTabView(it).id)
        }
    }

    /**
     * 展示闪屏页
     */

    private fun showSplash() {
        val transaction = supportFragmentManager.beginTransaction()
        mSplashFragment = supportFragmentManager.findFragmentByTag(SplashFragment::class.java.simpleName) as SplashFragment?
        val temp = mSplashFragment
        if (temp != null) {
            if (temp.isAdded) {
                transaction.show(temp).commitAllowingStateLoss()
            } else {
                transaction.remove(temp).commitAllowingStateLoss()
                mSplashFragment = SplashFragment.newInstance()
                transaction.add(R.id.main_content, temp, SplashFragment::class.java.simpleName).commitAllowingStateLoss()
            }
        } else {
            val tempSplash = SplashFragment.newInstance()
            mSplashFragment = tempSplash
            transaction.add(R.id.main_content, tempSplash, SplashFragment::class.java.simpleName).commitAllowingStateLoss()
        }
        mSplashFragment?.setSplashListener(object : SplashFragment.OnSplashListener {
            override fun onSplash(time: Long, totalTime: Long) {

            }
        })
    }

    private fun initFragment(id: Int) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        var fragment: Fragment? = null
        when (id) {
            R.id.home_page_tab -> {
                fragment = HomeFragment.newInstance()
                transaction.replace(R.id.content_layout, fragment)
            }
            R.id.community_tab -> {
                fragment = CommunityFragment.newInstance()
                transaction.replace(R.id.content_layout, fragment)
            }
            R.id.discovery_tab -> {
                fragment = DiscoveryFragment.newInstance()
                transaction.replace(R.id.content_layout, fragment)
            }
            R.id.mine_tab -> {
                fragment = MineFragment.newInstance()
                transaction.replace(R.id.content_layout, fragment)
            }
        }
        fragment?.let {
            transaction.show(fragment).commitAllowingStateLoss()
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        when (keyCode == KeyEvent.KEYCODE_BACK) {
            true -> {
                if (System.currentTimeMillis() - mExitTime > 2000) {
                    showToast("再按一次退出")
                    mExitTime = System.currentTimeMillis()
                } else {
                    super.onKeyDown(keyCode, event)
                }
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }

}
