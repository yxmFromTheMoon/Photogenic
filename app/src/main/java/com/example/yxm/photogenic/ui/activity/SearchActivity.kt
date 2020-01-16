package com.example.yxm.photogenic.ui.activity

import android.text.Editable
import android.text.TextWatcher
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
class SearchActivity: BaseActivity(){

    /**
     * UI
     */
    private lateinit var queryEditText: EditText
    private lateinit var queryCancelTv: TextView
    private lateinit var searchContentLayout: FrameLayout
    private lateinit var hotwordsFragment: HotWordsFragment
    private lateinit var searchResultFragment: SearchResultFragment

    override fun getLayoutId(): Int {
        return R.layout.activity_search
    }

    override fun initView() {
        queryEditText = edit_query
        queryCancelTv = query_cancel_tv
        searchContentLayout = search_result_content

        hotwordsFragment = HotWordsFragment.newInstance()
    }

    override fun initListener() {

        queryEditText.setOnClickListener {
            //开始搜索
            queryEditText.text.toString().let {queryWords ->
                showResultFragment(queryWords)
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

    private fun showResultFragment(queryWords: String){
        val fragment = SearchResultFragment.newInstance(queryWords)
    }

}