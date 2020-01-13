package com.example.yxm.photogenic.module.hotwords

import com.example.yxm.photogenic.base.BaseView
import com.example.yxm.photogenic.base.IPresenter

/**
 * Created by yxm on 2020-1-13
 * @function: 热搜
 */
interface HotWordsContract {
    interface IHotWordsView: BaseView{
        fun setHotWords(data: ArrayList<String>)

        fun showError(msg: String)
    }

    interface IHotWordsPresenter: IPresenter<IHotWordsView>{
        fun getHotWords()
    }
}