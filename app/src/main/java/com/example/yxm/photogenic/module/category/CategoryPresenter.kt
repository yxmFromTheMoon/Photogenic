package com.example.yxm.photogenic.module.category

import com.example.yxm.photogenic.base.BasePresenter
import com.example.yxm.photogenic.model.CategoriesModel
import io.reactivex.disposables.Disposable

/**
 * Created by yxm on 2020-1-13
 * @function: 分类presenter
 */
class CategoryPresenter: BasePresenter<CategoryContract.ICategoryView>(), CategoryContract.ICategoryPresenter{

    private val categoryMode: CategoriesModel by lazy {
        CategoriesModel()
    }

    override fun getCategoryData() {
        checkViewAttached()
        mRootView?.showLoading()
        val dispose = categoryMode.getCategoryData()
                .subscribe({
                    mRootView?.apply {
                        dismissLoading()
                        setCategory(it)
                    }
                },{
                    mRootView?.apply {
                        showError("获取分类列表失败")
                    }
                })
        addSubscribe(dispose)
    }
}