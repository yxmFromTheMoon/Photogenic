package com.example.yxm.photogenic.jsonview

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.example.lib_network.bean.BannerDataBean
import com.example.lib_network.okhttp.gsonutils.GsonUtils
import com.example.yxm.photogenic.model.DiscoveryModel

/**
 * Created by yxm on 2020-1-7
 * @function: 轮播图view
 */
class ViewBanner(context: Context,attributeSet: AttributeSet?,defStyleAttr: Int,model: DiscoveryModel = DiscoveryModel()) :
        FrameLayout(context,attributeSet,defStyleAttr),DiscoveryModel.DiscoveryModelListener{

    constructor(context: Context) : this(context,null,0)
    lateinit var bean: BannerDataBean

    init {
        model.setDiscoveryModelListener(this)
    }

    override fun onGetBannerData(jsonString: String) {
        bean = GsonUtils.jsonStringToBean(jsonString,BannerDataBean::class.java)
    }


}