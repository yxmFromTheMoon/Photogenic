package com.example.lib_share.share

import android.app.Dialog
import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.lib_share.R
import android.view.Gravity
import android.util.DisplayMetrics
import android.widget.Toast
import cn.sharesdk.framework.Platform
import com.chad.library.adapter.base.BaseQuickAdapter
import share.adapter.PlatformBean
import share.adapter.SharePlatformAdapter
import cn.sharesdk.framework.PlatformActionListener

/**
 *Created by yxm on 2020/2/22
 *@function 分享的对话框
 */
class ShareDialog : Dialog {

    private lateinit var mShareRv: RecyclerView
    private val dm: DisplayMetrics
    private val mAdapter: SharePlatformAdapter by lazy {
        SharePlatformAdapter()
    }
    var mShareType: Int = 0//指定分享类型
    var mShareTitle: String = ""//指定分享内容标题
    var mShareText: String = "" //指定分享内容文本
    var mSharePhoto: String = ""//指定分享本地图片
    var mShareTileUrl: String = ""
    var mShareSiteUrl: String = ""
    var mShareSite: String = ""
    var mUrl: String = ""//指定分享链接
    var mResourceUrl: String = ""

    constructor(context: Context) : this(context, R.style.SheetDialogStyle)

    constructor(context: Context, style: Int = R.style.SheetDialogStyle) : super(context, style) {
        setContentView(R.layout.dialog_share)
        dm = context.resources.displayMetrics
        initView()
        initListener()
    }

    private fun initView() {
        /**
         * 通过获取到dialog的window来控制dialog的宽高及位置
         */
        val dialogWindow = window
        dialogWindow!!.setGravity(Gravity.BOTTOM)
        val lp = dialogWindow.attributes
        lp.width = dm.widthPixels //设置宽度
        dialogWindow.attributes = lp
        dialogWindow.setBackgroundDrawableResource(android.R.color.transparent)
        mShareRv = findViewById(R.id.share_rv)
        mShareRv.run {
            layoutManager = GridLayoutManager(context, 5)
            adapter = mAdapter
        }
        initPlatform()
    }

    private fun initListener() {
        mAdapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
            val item = adapter.getItem(position) as PlatformBean
            when (item.name) {
                "QQ" -> {
                    shareData(ShareManager.PlatofrmType.QQ)
                    Toast.makeText(context,"QQ",Toast.LENGTH_SHORT).show()
                }
                "QQ空间" -> {
                    shareData(ShareManager.PlatofrmType.QZone)
                    Toast.makeText(context,"QQ空间",Toast.LENGTH_SHORT).show()
                }
                "微信" -> {
                    shareData(ShareManager.PlatofrmType.WeChat)
                    Toast.makeText(context,"微信",Toast.LENGTH_SHORT).show()
                }
                "朋友圈" -> {
                    shareData(ShareManager.PlatofrmType.WechatMoments)
                    Toast.makeText(context,"朋友圈",Toast.LENGTH_SHORT).show()
                }
                "微博" -> {
                    shareData(ShareManager.PlatofrmType.WeiBo)
                    Toast.makeText(context,"微博",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private val mListener = object : PlatformActionListener {
        override fun onComplete(platform: Platform, i: Int, hashMap: HashMap<String, Any>) {
            Toast.makeText(context, "分享成功", Toast.LENGTH_SHORT).show()
        }

        override fun onError(platform: Platform, i: Int, throwable: Throwable) {
            Toast.makeText(context, "分享失败${throwable.message}", Toast.LENGTH_SHORT).show()
        }

        override fun onCancel(platform: Platform, i: Int) {
            Toast.makeText(context, "分享取消", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * 分享数据
     */
    private fun shareData(platform: ShareManager.PlatofrmType) {
        val mData = ShareData()
        val params = Platform.ShareParams()
        params.apply {
            shareType = mShareType
            title = mShareTitle
            titleUrl = mShareTileUrl
            site = mShareSite
            siteUrl = mShareSiteUrl
            text = mShareText
            imagePath = mSharePhoto
            url = mUrl
        }
        mData.mPlatformType = platform
        mData.mShareParams = params
        ShareManager.shareData(mData, mListener)
    }

    private fun initPlatform() {
        val platformList = ArrayList<PlatformBean>()
        platformList.add(PlatformBean(R.mipmap.ic_action_share_moment_grey, "朋友圈"))
        platformList.add(PlatformBean(R.mipmap.ic_action_share_wechat_grey, "微信"))
        platformList.add(PlatformBean(R.mipmap.ic_action_share_weibo_grey, "微博"))
        platformList.add(PlatformBean(R.mipmap.ic_action_share_qq_grey, "QQ"))
        platformList.add(PlatformBean(R.mipmap.ic_action_share_qqzone_grey, "QQ空间"))
        mAdapter.setNewData(platformList)
    }
}