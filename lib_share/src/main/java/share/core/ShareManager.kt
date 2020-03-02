package com.example.lib_share.share

import android.content.Context
import cn.sharesdk.framework.Platform
import cn.sharesdk.framework.ShareSDK
import cn.sharesdk.wechat.moments.WechatMoments
import cn.sharesdk.wechat.friends.Wechat
import cn.sharesdk.tencent.qzone.QZone
import cn.sharesdk.tencent.qq.QQ
import cn.sharesdk.framework.PlatformActionListener
import cn.sharesdk.sina.weibo.SinaWeibo
import share.adapter.ShareType


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
        QQ, QZone, WeChat, WechatMoments, WeiBo
    }

    /**
     *@param shareImageUrl 要分享的图片url
     */
    fun shareImage(context: Context,shareImageUrl: String) {
        val dialog = ShareDialog(context)
        dialog.apply {
            mShareType = ShareType.SHARE_IMAGE
            mShareImageUrl = shareImageUrl
            show()
        }
    }

    /**
     * 分享图文链接
     * @param shareText 链接文本
     * @param shareTitle 链接标题
     * @param shareImageUrl 链接网络图片url
     * @param shareUrl 链接url
     */
    fun shareWebPage(context: Context, shareText: String,
                     shareTitle: String, shareImageUrl: String, shareUrl: String) {
        val dialog = ShareDialog(context)
        dialog.apply {
            mShareType = ShareType.SHARE_WEBPAGE
            mShareText = shareText
            mShareTitle = shareTitle
            mShareImageUrl = shareImageUrl
            mUrl = shareUrl
            show()
        }
    }
    /**
     * ****************还可以自己添加更多类型的分享，分享类型见ShareType*******************************
     */

}