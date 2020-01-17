package com.example.yxm.photogenic.ui.activity

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import com.example.yxm.photogenic.R
import com.example.yxm.photogenic.base.BaseActivity
import com.example.yxm.photogenic.ui.fragment.HotWordsFragment
import com.example.yxm.photogenic.ui.fragment.SearchResultFragment
import com.gyf.immersionbar.ktx.immersionBar
import kotlinx.android.synthetic.main.activity_search.*

/**
 * Created by yxm on 2020-1-13
 * @function: 搜索界面
 */
class SearchActivity : BaseActivity() {

    /**
     * UI
     */
    private lateinit var queryEditText: EditText
    private lateinit var queryCancelTv: TextView
    private lateinit var searchContentLayout: FrameLayout
    private var hotWordsFragment: HotWordsFragment? = null
    private var searchResultFragment: SearchResultFragment? = null
    private val transition = supportFragmentManager.beginTransaction()

    override fun getLayoutId(): Int {
        return R.layout.activity_search
    }

    override fun initView() {
        queryEditText = edit_query
        queryCancelTv = query_cancel_tv
        searchContentLayout = search_result_content
        initFragment()
    }

    override fun initListener() {

        queryEditText.setOnClickListener {
            //开始搜索
            if (!queryEditText.text.toString().isEmpty()) {
                showResultFragment(queryEditText.text.toString())
            }
        }
        //取消
        queryCancelTv.setOnClickListener {
            queryEditText.setText("")
            finish()
        }
    }

    override fun initData() {

    }

    override fun setStatusBarState() {
        immersionBar {
            statusBarColor(R.color.lightGray)
            keyboardEnable(true)
        }
    }

    /**
     * 初始化热搜词汇fragment
     */
    private fun initFragment() {
        if (hotWordsFragment == null) {
            hotWordsFragment = HotWordsFragment.newInstance()
        }
        transition.add(R.id.search_result_content, hotWordsFragment as Fragment)
        transition.hide(searchResultFragment as Fragment)
        transition.show(hotWordsFragment as Fragment)
        transition.commitAllowingStateLoss()
    }

    /**
     * 显示搜索结果fragment
     */
    private fun showResultFragment(queryWords: String) {
        checkNotNull(queryWords)
        transition.add(R.id.search_result_content, searchResultFragment as Fragment)
        transition.hide(hotWordsFragment as Fragment)
        transition.show(searchResultFragment as Fragment)
        transition.commitAllowingStateLoss()
    }

    /**
     * 隐藏fragment
     */
    private fun hideFragment(transition: FragmentTransaction) {

        hotWordsFragment?.let {
            transition.hide(it)
        }
        searchResultFragment?.let {
            transition.hide(it)
        }
    }

    /**
     * 确保fragment非空
     */
    private fun checkNotNull(queryWords: String) {
        if (searchResultFragment == null) {
            searchResultFragment = SearchResultFragment.newInstance(queryWords)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        hideFragment(transition)
    }
}