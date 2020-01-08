package com.example.yxm.photogenic.jsonview.viewdata

import org.json.JSONObject

/**
 * Created by yxm on 2020-1-7
 * @function: 获取不同类型的viewData
 */
data class ViewData(private val jsonObject: JSONObject) {

    var type: String = jsonObject.getString("type")

    var json: JSONObject = jsonObject.getJSONObject("data")
}