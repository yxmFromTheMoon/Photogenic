package com.example.yxm.photogenic.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.example.lib_imageloader.ImageLoaderManager
import com.example.yxm.photogenic.R
import com.example.yxm.photogenic.base.BaseFragment
import com.example.yxm.photogenic.event.ToggleUiEvent
import com.example.yxm.photogenic.widget.SavePictureSheetDialog
import com.example.yxm.photogenic.widget.SlideDownCloseImageView
import kotlinx.android.synthetic.main.fragment_picture.*
import org.greenrobot.eventbus.EventBus

/**
 * @author yxm
 * 2020/4/21 13:04
 * @function 查看图片fragment
 */
class PictureFragment : BaseFragment() {

    private lateinit var picture: SlideDownCloseImageView

    val url: String by lazy {
        arguments?.getString(URL) ?: ""
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_picture
    }

    override fun initView(view: View) {
        picture = picture_view
    }

    override fun initListener() {

        picture.setOnSlideDownListener(object : SlideDownCloseImageView.OnSlideOverListener {
            override fun onLongClick() {
                val disposable = rxPermission.request("android.permission.WRITE_EXTERNAL_STORAGE")
                        .subscribe {
                            if (it) {
                                SavePictureSheetDialog(mContext, url).show()
                            }
                        }
                disposable.dispose()
            }

            override fun onSlideOver() {
                activity?.finish()
            }

            override fun onViewClick() {
                EventBus.getDefault().post(ToggleUiEvent())
            }
        })
    }

    override fun lazyLoad() {
        ImageLoaderManager.displayBigPicture(picture, url)
        Log.i("pictureUrl",url)
    }

    companion object {

        const val URL = "url"

        fun newInstance(url: String) = PictureFragment().apply {
            val bundle = Bundle()
            bundle.putString(URL, url)
            arguments = bundle
        }
    }
}