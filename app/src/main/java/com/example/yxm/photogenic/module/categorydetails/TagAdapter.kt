package com.example.yxm.photogenic.module.categorydetails

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.lib_network.bean.CommonVideoBean
import com.example.yxm.photogenic.R

/**
 *Created by yxm on 2020/2/10
 *@function  视频标签adapter
 */
class TagAdapter : BaseQuickAdapter<CommonVideoBean.ResultBean.ResultData.TagBean, BaseViewHolder>(R.layout.item_tag) {

    override fun convert(holder: BaseViewHolder, item: CommonVideoBean.ResultBean.ResultData.TagBean) {
        with(holder) {
            setText(R.id.tag_tv, item.name)
        }
    }
}