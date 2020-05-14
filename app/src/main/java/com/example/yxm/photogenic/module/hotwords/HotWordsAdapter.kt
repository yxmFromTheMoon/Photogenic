package com.example.yxm.photogenic.module.hotwords

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.yxm.photogenic.R

/**
 * Created by yxm on 2020-1-16
 * @function:热搜适配器
 */
class HotWordsAdapter: BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_hot_words) {

    override fun convert(holder: BaseViewHolder, item: String) {

        with(holder){
            setText(R.id.key_word,item)
        }
    }
}