package com.example.yxm.photogenic.module.category

import com.example.lib_network.bean.CategoriesBean
import com.example.yxm.photogenic.base.BaseView
import com.example.yxm.photogenic.base.IPresenter

/**
 * Created by yxm on 2020-1-13
 * @function: 分类目录contract
 */
interface CategoryContract {
    interface ICategoryView: BaseView{
        /**
         * 设置数据
         */
        fun setCategory(data: ArrayList<CategoriesBean>)

        /**
         * 错误信息
         */
        fun showError(msg: String)
    }

    interface ICategoryPresenter: IPresenter<ICategoryView>{
        /**
         * 获取分类数据
         */
        fun getCategoryData()
    }
}