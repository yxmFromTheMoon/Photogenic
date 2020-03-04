package com.example.yxm.photogenic.ui.activity

import android.support.v4.view.ViewPager
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.lib_share.share.ShareManager
import com.example.yxm.photogenic.R
import com.example.yxm.photogenic.R.id.picture_layout
import com.example.yxm.photogenic.application.MyApplication
import com.example.yxm.photogenic.base.BaseActivity
import com.example.yxm.photogenic.module.picturepreview.PicturePagerAdapter
import com.example.yxm.photogenic.module.picturepreview.PicturePreviewContract
import com.example.yxm.photogenic.module.picturepreview.PicturePreviewPresenter
import com.example.yxm.photogenic.utils.ScreenHelper
import com.example.yxm.photogenic.utils.ScreenHelper.scaleImage
import com.gyf.immersionbar.ktx.immersionBar
import kotlinx.android.synthetic.main.activity_preview_picture.*

/**
 * Created by yxm on 2020/3/1
 * @function 图片查看页面
 */
class PicturePreviewActivity : BaseActivity(), PicturePreviewContract.IPictureView {

    /**
     * data,图片url集合
     */
    private var currentPosition = 0

    //图片原始宽
    private var pictureWidth = 0L
    //图片原始高
    private var pictureHeight = 0L

    private lateinit var urls: ArrayList<String>

    private var mPagerAdapter: PicturePagerAdapter? = null

    private val mPresenter: PicturePreviewPresenter by lazy {
        PicturePreviewPresenter()
    }
    /**
     */
    private lateinit var mCloseIv: ImageView
    private lateinit var mShareIv: ImageView
    private lateinit var mPictureIndex: TextView
    private lateinit var mViewPager: ViewPager

    init {
        mPresenter.attachView(this)
    }

    override fun setStatusBarState() {
        super.setStatusBarState()
        immersionBar {
            titleBar(status_bar_view)
            statusBarColor(R.color.black)
        }
    }

    override fun initDataBeforeView() {
        super.initDataBeforeView()
        urls = intent.getStringArrayListExtra("urls")
        pictureWidth = intent.getLongExtra("width", ScreenHelper.getScreenWidth(mContext).toLong())
        pictureHeight = intent.getLongExtra("height", ScreenHelper.getScreenHeight(mContext).toLong())
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_preview_picture
    }

    override fun initView() {
        mCloseIv = close_iv
        mShareIv = picture_share_iv
        mPictureIndex = picture_index
        mViewPager = picture_vp
        mPagerAdapter = PicturePagerAdapter(urls)

        mViewPager.apply {
            adapter = mPagerAdapter
            offscreenPageLimit = urls.size
            currentItem = 0
            layoutParams = scaleImage(this.layoutParams as RelativeLayout.LayoutParams, pictureWidth, pictureHeight)
        }
        if (urls.size == 1) {
            mPictureIndex.visibility = View.INVISIBLE
        }
        mPictureIndex.text = "${currentPosition + 1} / ${urls.size}"

    }

    override fun initListener() {
        mCloseIv.setOnClickListener {
            finish()
        }

        mViewPager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(positon: Int) {
                currentPosition = positon
                mPictureIndex.text = "${currentPosition + 1} / ${urls.size}"
            }
        })
        //分享
        mShareIv.setOnClickListener {
            ShareManager.shareImage(mContext,
                    urls[currentPosition])
            Log.i("PicturePreview", "${urls[currentPosition]}+$currentPosition")
        }

        picture_layout.setOnClickListener {
            toggleUi()
        }
        mPagerAdapter?.setOnPhotoViewClickListener(object : PicturePagerAdapter.OnPhotoViewClickListener {
            override fun onPhotoViewClick() {
                toggleUi()
            }

            override fun onSlideDown() {
                this@PicturePreviewActivity.finish()
            }
        })
    }

    override fun initData() {}

    override fun setUrls(urls: ArrayList<String>) {}

    /**
     * UI点击隐藏与显示
     */
    private fun toggleUi() {
        if (mShareIv.visibility == View.INVISIBLE) {
            mShareIv.visibility = View.VISIBLE
        } else {
            mShareIv.visibility = View.INVISIBLE
        }
        if (mCloseIv.visibility == View.INVISIBLE) {
            mCloseIv.visibility = View.VISIBLE
        } else {
            mCloseIv.visibility = View.INVISIBLE
        }
    }

    override fun showError(msg: String) {}
    override fun showLoading() {}
    override fun dismissLoading() {}
    override fun showSuccess() {}

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

}