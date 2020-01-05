package com.example.lib_network.bean

/**
 * Created by yxm on 2019-12-31
 * @function: 分类bean
 */

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