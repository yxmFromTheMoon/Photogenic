package com.example.yxm.photogenic.ui.activity

import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.yxm.photogenic.R
import com.example.yxm.photogenic.base.BaseActivity
import com.example.yxm.photogenic.module.hotwords.HotWordsEvent
import com.example.yxm.photogenic.ui.fragment.HotWordsFragment
import com.example.yxm.photogenic.ui.fragment.SearchResultFragment
import com.example.yxm.photogenic.utils.KeyBoardHelper
import com.gyf.immersionbar.ktx.immersionBar
import kotlinx.android.synthetic.main.activity_search.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

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
    private lateinit var statusBarView: View
    private lateinit var searchContentLayout: FrameLayout
    private var hotWordsFragment: HotWordsFragment? = null
    private var searchResultFragment: SearchResultFragment? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_search
    }

    override fun initView() {
        EventBus.getDefault().register(this)
        queryEditText = edit_query
        queryCancelTv = query_cancel_tv
        searchContentLayout = search_result_content
        statusBarView = status_bar_view
        KeyBoardHelper.openKeyBoard(queryEditText)
        initFragment()
    }

    override fun initListener() {
        queryEditText.setOnEditorActionListener { v, actionId, event ->
            val keyWord: String? = queryEditText.text.toString().trim()
            if ((actionId == EditorInfo.IME_ACTION_SEARCH || (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER)) && !keyWord.isNullOrEmpty()) {
                KeyBoardHelper.hideKeyBoard(queryEditText)
                //开始搜索
                showResultFragment(keyWord)
                return@setOnEditorActionListener true
            } else {
                showToast("请输入你感兴趣的关键词")
            }
            return@setOnEditorActionListener false
        }

        queryEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                removeResultFragment()
                initFragment()
            }

        })
        //取消
        queryCancelTv.setOnClickListener {
            KeyBoardHelper.hideKeyBoard(queryEditText)
            finish()
        }
    }

    override fun initData() {

    }

    override fun setStatusBarState() {
        immersionBar {
            titleBar(statusBarView)
            statusBarColor(R.color.statusBarColor)
            statusBarDarkFont(true)
        }
    }

    /**
     * 初始化热搜词汇fragment
     */
    private fun initFragment() {
        val transition = supportFragmentManager.beginTransaction()
        if (hotWordsFragment == null) {
            hotWordsFragment = HotWordsFragment.newInstance()
            transition.add(R.id.search_result_content, hotWordsFragment as Fragment)
        }
        searchResultFragment?.let {
            transition.hide(it)
        }
        transition.show(hotWordsFragment as Fragment).commitAllowingStateLoss()
    }

    /**
     * 移除搜索结果fragment
     */
    private fun removeResultFragment() {
        supportFragmentManager?.let {
            val fragment = searchResultFragment
            if (fragment != null) {
                it.beginTransaction().remove(fragment).commitAllowingStateLoss()
            }
        }
    }

    /**
     * 显示搜索结果fragment
     */
    private fun showResultFragment(queryWords: String?) {
        val transition = supportFragmentManager.beginTransaction()
        searchResultFragment = SearchResultFragment.newInstance(queryWords)
        transition.add(R.id.search_result_content, searchResultFragment as Fragment)
        hotWordsFragment?.let {
            transition.hide(it)
        }
        transition.show(searchResultFragment as Fragment).commitAllowingStateLoss()
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
        KeyBoardHelper.hideKeyBoard(queryEditText)
    }

    /**
     * 接收热搜词汇点击事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun getHotWordsFromFragment(hotWordsEvent: HotWordsEvent) {
        val hotWords = hotWordsEvent.hotWords
        queryEditText.text = Editable.Factory.getInstance().newEditable(hotWords)
        showResultFragment(hotWords)
    }
}