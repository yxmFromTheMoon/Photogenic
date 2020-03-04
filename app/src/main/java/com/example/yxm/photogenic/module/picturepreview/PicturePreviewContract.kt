package com.example.yxm.photogenic.module.picturepreview

import com.example.yxm.photogenic.base.BaseView
import com.example.yxm.photogenic.base.IPresenter

/**
 *Created by yxm on 2020/3/2
 *@function 图片预览contract
 */
interface PicturePreviewContract {
    interface IPictureView: BaseView{
        //设置图片url集合
        fun setUrls(urls: ArrayList<String>)
    }

    interface IPicturePresenter: IPresenter<IPictureView>{
        //加载图片url集合
        fun loadUrls(urls: ArrayList<String>)
    }
}