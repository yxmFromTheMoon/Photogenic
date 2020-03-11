package com.example.lib_network.okhttp.gsonutils

import com.google.gson.Gson

/**
 * Created by yxm on 2020-1-9
 * @function: gson转换工具类
 */
object GsonUtils {
    private val gson = Gson()

    fun<T> jsonStringToBean(jsonString: String,clazz: Class<T>): T{
        return gson.fromJson(jsonString,clazz)
    }

}