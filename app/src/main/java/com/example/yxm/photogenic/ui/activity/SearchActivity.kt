package com.example.yxm.photogenic.ui.activity

import com.example.yxm.photogenic.R
import com.example.yxm.photogenic.base.BaseActivity
import com.example.yxm.photogenic.module.hotwords.HotWordsContract
import com.example.yxm.photogenic.module.hotwords.HotWordsPresenter

/**
 * Created by yxm on 2020-1-13
 * @function: 搜索界面
 */
class SearchActivity: BaseActivity(),HotWordsContract.IHotWordsView {

    private val mHotWordsPresenter: HotWordsPresenter by lazy {
        HotWordsPresenter()
    }

    init {
        mHotWordsPresenter.attachView(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_search
    }

    override fun initView() {

    }

    override fun initListener() {

    }
    override fun initData() {
        mHotWordsPresenter.getHotWords()
    }

    override fun setHotWords(data: ArrayList<String>) {

    }

    override fun showError(msg: String) {

    }

    override fun showLoading() {

    }

    override fun dismissLoading() {

    }

}