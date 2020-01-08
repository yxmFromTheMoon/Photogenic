package com.example.yxm.photogenic.model

import com.example.lib_network.api.RetrofitManager
import com.example.lib_network.api.constants.UrlConstants
import com.example.yxm.photogenic.jsonview.viewdata.ViewData
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call


/**
 * Created by yxm on 2020-1-7
 * @function: 首页发现model
 */
class DiscoveryModel {

    private val viewDatas: HashMap<String,ViewData> = HashMap()

    init {
        val jsonString = RetrofitManager.getApi(UrlConstants.baseUrlKaiYan)
                .getDiscovery().execute().body()?.string()
        val json = JSONObject(jsonString)
        val jsonArray = json.getJSONArray("itemList")
        for (i in 0 until jsonArray.length()) {
            val viewData = ViewData(jsonArray.getJSONObject(i))
            viewDatas.put(viewData.type, viewData)
        }
    }


    private fun getBannerJsonData(): JSONObject?{

        return viewDatas.get("horizontalScrollCard")?.json
    }

    fun getBannerList(): ArrayList<String>?{

        getBannerJsonData()?.run {
            getJSONObject("data")
        }?.run {
            getJSONArray("itemList")
        }?.let {
            val list = ArrayList<String>()
            for (i in 0 until it.length()){
                list.add(it.getJSONObject(i).getString("image"))
            }
            return list
        }
        return null
    }

}