package com.example.yxm.photogenic.module.searchresult

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.lib_network.bean.CommonVideoBean
import com.example.yxm.photogenic.R

/**
 * Created by yxm on 2020-1-17
 * @function: 搜索结果adapter
 */
class SearchResultAdapter: BaseQuickAdapter<CommonVideoBean.ResultBean,BaseViewHolder>(R.layout.item_video_bean) {

    override fun convert(helper: BaseViewHolder?, item: CommonVideoBean.ResultBean?) {

    }
}