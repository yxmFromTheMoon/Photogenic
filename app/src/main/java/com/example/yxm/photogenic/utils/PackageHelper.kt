package com.example.yxm.photogenic.utils

import com.example.yxm.photogenic.application.MyApplication

/**
 *Created by yxm on 2020/3/4
 *@function 包相关工具类
 */
object PackageHelper {

    /**
     * 获取APP版本号
     */
    fun getAppVersion(): String {
        val packageManager = MyApplication.instance.packageManager
        val packageInfo = packageManager.getPackageInfo(MyApplication.instance.packageName, 0)
        return packageInfo.versionName
    }
}