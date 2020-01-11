package com.example.yxm.photogenic.ui.activity

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.view.KeyEvent
import android.widget.FrameLayout
import com.example.yxm.photogenic.R
import com.example.yxm.photogenic.base.BaseActivity
import com.yinglan.alphatabs.AlphaTabView
import com.yinglan.alphatabs.AlphaTabsIndicator
import kotlinx.android.synthetic.main.activity_main.*

/**
 * MainActivity
 */
class MainActivity : BaseActivity(){
    private lateinit var contentLayout: FrameLayout
    private lateinit var homePageTab: AlphaTabView
    private lateinit var communityTab: AlphaTabView
    private lateinit var discoveryTab: AlphaTabView
    private lateinit var mineTab: AlphaTabView
    private lateinit var mAlphaIndicator: AlphaTabsIndicator
    private var mExitTime: Long = 0

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        contentLayout = content_layout
        homePageTab = home_page_tab
        communityTab = community_tab
        discoveryTab = discovery_tab
        mineTab = mine_tab
        mAlphaIndicator = alphaIndicator
    }

    override fun initData() {

    }

    override fun initListener() {
        mAlphaIndicator.setOnTabChangedListner {
            initFragment(mAlphaIndicator.getTabView(it).id)
        }
    }

    private fun initFragment(id: Int){
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        var fragment: Fragment? = null
        when(id){
            R.id.home_page_tab -> {
                showToast("首页")
            }
            R.id.community_tab -> {
                showToast("社区")
            }
            R.id.discovery_tab -> {
                showToast("发现")
            }
            R.id.mine_tab -> {
                showToast("我的")
            }
        }
        fragment?.let{
            hideFragment(transaction)
            transaction.show(fragment)
            transaction.commitAllowingStateLoss()
        }
    }

    private fun hideFragment(transaction: FragmentTransaction){
        /**
         * TODO
         */
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        when(keyCode == KeyEvent.KEYCODE_BACK) {
            true -> {
                if (System.currentTimeMillis() - mExitTime > 2000) {
                    showToast("再按一次退出")
                    mExitTime = System.currentTimeMillis()
                }else{
                    System.exit(0)
                }
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }
}
