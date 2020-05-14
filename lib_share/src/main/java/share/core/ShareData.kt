package com.example.lib_share.share

import cn.sharesdk.framework.Platform.ShareParams
import share.core.ShareManager


/**
 *Created by yxm on 2020/2/22
 *@function 分享数据
 */
class ShareData {
    /**
     * 要分享到的平台
     */
    var mPlatformType: ShareManager.PlatofrmType? = null

    /**
     * 要分享到的平台的参数
     */
    var mShareParams: ShareParams? = null
}