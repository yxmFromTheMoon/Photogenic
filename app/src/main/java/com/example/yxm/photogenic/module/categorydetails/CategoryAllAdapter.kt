package com.example.yxm.photogenic.module.categorydetails

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.lib_imageloader.ImageLoaderManager
import com.example.lib_network.bean.CategoriesBean
import com.example.yxm.photogenic.R

/**
 *Created by yxm on 2020/2/15
 *@function 全部分类adapter
 */
class CategoryAllAdapter: BaseQuickAdapter<CategoriesBean, BaseViewHolder>(R.layout.item_big_category) {

    override fun convert(helper: BaseViewHolder?, item: CategoriesBean) {
        helper?.run {
            ImageLoaderManager.displayImageForView(getView(R.id.category_cover_image),item.bgPicture)
            setText(R.id.category_name,"#${item.name}")
        }
    }
}