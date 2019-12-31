package com.example.lib_network.model

import com.google.gson.annotations.SerializedName

/**
 * Created by yxm on 2019-12-31
 * @function: 分类bean
 */

data class CategoriesResult(

        @SerializedName("result")
        var result: List<CategoriesBean> = emptyList()
)

data class CategoriesBean(

        var id: Int,
        var name: String,
        var alisa: String?,
        var description: String,
        var bgPicture: String,
        var bgColor: String,
        var headerImage: String,
        var defaultAuthorId: Int,
        var tagId: Int
)