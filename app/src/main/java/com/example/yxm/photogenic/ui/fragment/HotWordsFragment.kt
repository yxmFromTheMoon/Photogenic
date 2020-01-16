package com.example.yxm.photogenic.ui.fragment

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.yxm.photogenic.R
import com.example.yxm.photogenic.base.BaseFragment
import com.example.yxm.photogenic.module.hotwords.HotWordsAdapter
import com.example.yxm.photogenic.module.hotwords.HotWordsContract
import com.example.yxm.photogenic.module.hotwords.HotWordsPresenter
import kotlinx.android.synthetic.main.fragment_hot_words.view.*

/**
 * Created by yxm on 2020-1-16
 * @function: 热搜词汇fragment
 */
class HotWordsFragment: BaseFragment(), HotWordsContract.IHotWordsView{

    private lateinit var hotWordsRv: RecyclerView

    private val mAdapter: HotWordsAdapter by lazy {
        HotWordsAdapter()
    }

    private val mHotWordsPresenter: HotWordsPresenter by lazy {
        HotWordsPresenter()
    }

    init {
        mHotWordsPresenter.attachView(this)
    }

    override fun setHotWords(data: ArrayList<String>) {
        mAdapter.setNewData(data)
    }

    override fun showError(msg: String) {
        showErrorToast("获取热搜失败")
    }

    override fun showLoading() {
    }

    override fun dismissLoading() {
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_hot_words
    }

    override fun initView(view: View) {
        hotWordsRv = view.hot_words_rv
        hotWordsRv.run {
            layoutManager = LinearLayoutManager(mContext)
            adapter = mAdapter
        }
    }

    override fun initListener() {
        mAdapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
            val item = adapter.getItem(position) as String

        }
    }

    override fun lazyLoad() {
        mHotWordsPresenter.getHotWords()
    }

    override fun initImmersionBar() {
    }

    companion object {
        fun newInstance(): HotWordsFragment{
            return HotWordsFragment()
        }
    }
}