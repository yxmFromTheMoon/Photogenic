package com.example.yxm.photogenic.module.discovery

import com.example.lib_network.bean.BannerDataBean
import com.example.yxm.photogenic.base.BaseView
import com.example.yxm.photogenic.base.IPresenter

/**
 * Created by yxm on 2020-1-14
 * @function:
 */
interface DiscoveryContract {
    interface IDiscoveryView: BaseView{
        fun setBannerList(data: ArrayList<String>)

        fun showError(msg: String)
    }

    interface IDiscoveryPresenter: IPresenter<IDiscoveryView>{
        fun getBannerData()
    }
}