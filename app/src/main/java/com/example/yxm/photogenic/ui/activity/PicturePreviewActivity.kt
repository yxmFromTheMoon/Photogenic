package com.example.yxm.photogenic.ui.activity

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import share.core.ShareManager
import com.example.yxm.photogenic.R
import com.example.yxm.photogenic.base.BaseActivity
import com.example.yxm.photogenic.base.BaseFragment
import com.example.yxm.photogenic.event.ToggleUiEvent
import com.example.yxm.photogenic.module.picturepreview.PictureAdapter
import com.example.yxm.photogenic.module.picturepreview.PicturePreviewContract
import com.example.yxm.photogenic.module.picturepreview.PicturePreviewPresenter
import com.example.yxm.photogenic.ui.fragment.PictureFragment
import com.example.yxm.photogenic.utils.ScreenHelper
import com.example.yxm.photogenic.utils.ScreenHelper.scaleImage
import com.gyf.immersionbar.ktx.immersionBar
import kotlinx.android.synthetic.main.activity_preview_picture.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Created by yxm on 2020/3/1
 * @function 图片查看页面
 */
class PicturePreviewActivity : BaseActivity(), PicturePreviewContract.IPictureView {

    /**
     * data,图片url集合
     */
    private var currentPosition = 0

    private var urls: ArrayList<String>? = null

    private var mPictureAdapter: PictureAdapter? = null

    private val mPresenter: PicturePreviewPresenter by lazy {
        PicturePreviewPresenter()
    }
    /**
     */
    private lateinit var mCloseIv: ImageView
    private lateinit var mShareIv: ImageView
    private lateinit var mPictureIndex: TextView
    private lateinit var mViewPager: ViewPager2

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
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_preview_picture
    }

    override fun initView() {
        EventBus.getDefault().register(this)
        mCloseIv = close_iv
        mShareIv = picture_share_iv
        mPictureIndex = picture_index
        mViewPager = picture_vp
        val fragments = ArrayList<BaseFragment>()
        urls?.forEach {
            fragments.add(PictureFragment.newInstance(it))
        }
        mPictureAdapter = PictureAdapter(mContext as FragmentActivity, fragments)

        mViewPager.apply {
            adapter = mPictureAdapter
            offscreenPageLimit = urls?.size ?: 1
            currentItem = 0
        }
        if (urls?.size == 1) {
            mPictureIndex.visibility = View.INVISIBLE
        }
        mPictureIndex.text = "${currentPosition + 1} / ${urls?.size}"

    }

    override fun initListener() {
        mCloseIv.setOnClickListener {
            finish()
        }

        mViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                currentPosition = position
                mPictureIndex.text = "${currentPosition + 1} / ${urls?.size}"
            }
        })

        //分享
        mShareIv.setOnClickListener {
            ShareManager.shareImage(mContext,
                    urls!![currentPosition])
            Log.i("PicturePreview", "${urls!![currentPosition]}+$currentPosition")
        }

        picture_layout.setOnClickListener {
            toggleUi()
        }
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun toggleUI(event: ToggleUiEvent) {
        toggleUi()
    }

    override fun showError(msg: String) {}
    override fun showLoading() {}
    override fun dismissLoading() {}
    override fun showSuccess() {}

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
        mPresenter.detachView()
    }

}