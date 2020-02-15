package com.example.yxm.photogenic.ui.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.view.KeyEvent
import android.widget.FrameLayout
import com.example.yxm.photogenic.R
import com.example.yxm.photogenic.base.BaseActivity
import com.example.yxm.photogenic.ui.fragment.DiscoveryFragment
import com.example.yxm.photogenic.ui.fragment.HomePageRecommendFragment
import com.example.yxm.photogenic.ui.fragment.MineFragment
import com.example.yxm.photogenic.ui.fragment.SplashFragment
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
    private var mHomePageRecommendFragment: HomePageRecommendFragment? = null
    private var mDiscoveryFragment: DiscoveryFragment? = null
    private var mMineFragment: MineFragment? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
    }

    override fun setStatusBarState() {
        immersionBar {
            showStatusBar()
            statusBarDarkFont(true)
        }
    }

    override fun initView() {
        contentLayout = content_layout
        homePageTab = home_page_tab
        communityTab = community_tab
        discoveryTab = discovery_tab
        mineTab = mine_tab
        mAlphaIndicator = alphaIndicator
        showSplash()
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
                showToast("首页")
            }
            R.id.community_tab -> {
                showToast("社区")
            }
            R.id.discovery_tab -> {
                if(mDiscoveryFragment == null){
                    mDiscoveryFragment = DiscoveryFragment.newInstance()
                    fragment = mDiscoveryFragment
                    transaction.add(R.id.content_layout,fragment!!)
                }
            }
            R.id.mine_tab -> {
                showToast("我的")
            }
        }
        fragment?.let {
            hideFragment(transaction)
            transaction.show(fragment).commitAllowingStateLoss()
        }
    }

    private fun hideFragment(transaction: FragmentTransaction) {
        mHomePageRecommendFragment?.let {
            transaction.hide(it)
        }
        mDiscoveryFragment?.let {
            transaction.hide(it)
        }
        mMineFragment?.let {
            transaction.hide(it)
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
