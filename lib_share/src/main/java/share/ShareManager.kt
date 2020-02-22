package com.example.lib_share.share

import cn.sharesdk.framework.Platform
import cn.sharesdk.framework.ShareSDK
import cn.sharesdk.wechat.moments.WechatMoments
import cn.sharesdk.wechat.friends.Wechat
import cn.sharesdk.tencent.qzone.QZone
import cn.sharesdk.tencent.qq.QQ
import cn.sharesdk.framework.PlatformActionListener
import cn.sharesdk.sina.weibo.SinaWeibo


/**
 *Created by yxm on 2020/2/22
 *@function 分享管理类
 */
object ShareManager {

    /**
     * 要分享到的平台
     */
    private var mCurrentPlatform: Platform? = null

    /**
     * 分享数据到不同平台
     */
    fun shareData(shareData: ShareData, listener: PlatformActionListener) {
        when (shareData.mPlatformType) {
            ShareManager.PlatofrmType.QQ -> mCurrentPlatform = ShareSDK.getPlatform(QQ.NAME)
            ShareManager.PlatofrmType.QZone -> mCurrentPlatform = ShareSDK.getPlatform(QZone.NAME)
            ShareManager.PlatofrmType.WeChat -> mCurrentPlatform = ShareSDK.getPlatform(Wechat.NAME)
            ShareManager.PlatofrmType.WechatMoments -> mCurrentPlatform = ShareSDK.getPlatform(WechatMoments.NAME)
            ShareManager.PlatofrmType.WeiBo -> mCurrentPlatform = ShareSDK.getPlatform(SinaWeibo.NAME)
            else -> {
            }
        }
        mCurrentPlatform?.platformActionListener = listener //由应用层去处理回调,分享平台不关心。
        mCurrentPlatform?.share(shareData.mShareParams)
    }

    /**
     * @author 应用程序需要的平台
     */
    enum class PlatofrmType {
        QQ, QZone, WeChat, WechatMoments,WeiBo
    }
}