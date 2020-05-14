package share.core

import android.content.Context
import cn.sharesdk.framework.Platform
import cn.sharesdk.framework.PlatformActionListener
import cn.sharesdk.framework.ShareSDK
import cn.sharesdk.sina.weibo.SinaWeibo
import cn.sharesdk.tencent.qq.QQ
import cn.sharesdk.tencent.qzone.QZone
import cn.sharesdk.wechat.friends.Wechat
import cn.sharesdk.wechat.moments.WechatMoments
import com.example.lib_share.share.ShareData
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
            PlatofrmType.QQ -> mCurrentPlatform = ShareSDK.getPlatform(QQ.NAME)
            PlatofrmType.QZone -> mCurrentPlatform = ShareSDK.getPlatform(QZone.NAME)
            PlatofrmType.WeChat -> mCurrentPlatform = ShareSDK.getPlatform(Wechat.NAME)
            PlatofrmType.WechatMoments -> mCurrentPlatform = ShareSDK.getPlatform(WechatMoments.NAME)
            PlatofrmType.WeiBo -> mCurrentPlatform = ShareSDK.getPlatform(SinaWeibo.NAME)
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
     * @param url 分享的链接
     */
    fun shareUrl(context: Context,url: String){
        val dialog = ShareDialog(context)
        dialog.apply {
            mShareType = ShareType.SHARE_TEXT
            mShareText = url
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
     * 分享图文链接
     * @param shareText 链接文本
     * @param shareTitle 链接标题
     * @param shareImageUrl 本地图片url
     * @param shareUrl 链接url
     */
    fun shareWebPageWithLocalImage(context: Context, shareText: String,
                     shareTitle: String, shareImageUrl: String, shareUrl: String) {
        val dialog = ShareDialog(context)
        dialog.apply {
            mShareType = ShareType.SHARE_WEBPAGE
            mShareText = shareText
            mShareTitle = shareTitle
            mSharePhoto = shareImageUrl
            mUrl = shareUrl
            show()
        }
    }

    /**
     * ****************还可以自己添加更多类型的分享，分享类型见ShareType*******************************
     */

}