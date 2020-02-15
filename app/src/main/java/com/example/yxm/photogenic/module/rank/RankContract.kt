package com.example.yxm.photogenic.module.rank

import com.example.lib_network.bean.CommonVideoBean
import com.example.yxm.photogenic.base.BaseView
import com.example.yxm.photogenic.base.IPresenter

/**
 * Created by yxm on 2020-1-15
 * @function:排行Contract
 */
interface RankContract {

    interface IRankView: BaseView{
        val strategy: String

        fun finishRefresh()

        fun setData(data: ArrayList<CommonVideoBean.ResultBean>)

    }

    interface IRankPresenter: IPresenter<IRankView>{

        fun getRankData(strategy: String)
    }
}