package com.example.yxm.photogenic.module.picturepreview

import com.example.yxm.photogenic.base.BasePresenter

/**
 *Created by yxm on 2020/3/2
 *@function 图片预览presenter
 */
class PicturePreviewPresenter: BasePresenter<PicturePreviewContract.IPictureView>(),
        PicturePreviewContract.IPicturePresenter{

    override fun loadUrls(urls: ArrayList<String>) {
        checkViewAttached()
        mRootView?.setUrls(urls)
    }

}