package com.example.yxm.photogenic.model

import android.util.Log
import com.example.lib_network.api.constants.UrlConstants
import com.example.lib_network.okhttp.RequestCenter
import com.example.lib_network.okhttp.listeners.DisposeDataListener
import com.example.yxm.photogenic.jsonview.viewdata.ViewData
import org.json.JSONObject


/**
 * Created by yxm on 2020-1-7
 * @function: 发现接口model，这里只获取了轮播图
 */
class DiscoveryModel : DisposeDataListener {

    private val viewDatas: HashMap<String, ViewData> = HashMap()
    private lateinit var mListener: DiscoveryModelListener

    init {
        RequestCenter.getRequest("${UrlConstants.baseUrlKaiYan}v5/index/tab/discovery", null, this, null)
    }

    override fun onSuccess(responseObj: Any?) {

        responseObj.run {
            this as String
            JSONObject(this)
        }.run {
            getJSONArray("itemList")
        }.run {
            for (i in 0 until this.length()) {
                val viewData = ViewData(this.getJSONObject(i))
                viewDatas.put(viewData.type, viewData)
            }
        }
        mListener.onGetBannerData(viewDatas.get("horizontalScrollCard")!!.json.toString())
    }

    override fun onFailure(reasonObj: Any?) {
        Log.e("DiscoverModel", reasonObj.toString())
    }

    fun setDiscoveryModelListener(listener: DiscoveryModelListener){
        mListener = listener
    }

    interface DiscoveryModelListener{

        //获取轮播图
        fun onGetBannerData(jsonString: String)
        //后续还可添加更多类型的数据，这里只获取了轮播图
    }

}