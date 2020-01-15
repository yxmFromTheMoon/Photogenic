package com.example.yxm.photogenic.module.rank

import com.example.yxm.photogenic.base.BaseView
import com.example.yxm.photogenic.base.IPresenter

/**
 * Created by yxm on 2020-1-15
 * @function:排行Contract
 */
interface RankContract {
    interface IRankView: BaseView{
        val title: String
    }

    interface IRankPresenter: IPresenter<IRankView>{

    }
}