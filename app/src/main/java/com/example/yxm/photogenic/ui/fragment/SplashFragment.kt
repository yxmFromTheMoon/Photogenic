package com.example.yxm.photogenic.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.lib_imageloader.ImageLoaderManager
import com.example.lib_imageloader.Utils
import com.example.yxm.photogenic.R
import com.example.yxm.photogenic.base.BaseFragment
import com.example.yxm.photogenic.base.BaseImmersionFragment
import com.example.yxm.photogenic.rxschedulers.IoMainScheduler
import com.gyf.immersionbar.ImmersionBar
import com.gyf.immersionbar.ktx.hideStatusBar
import com.gyf.immersionbar.ktx.showStatusBar
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_splash.*
import java.util.concurrent.TimeUnit


/**
 * Created by yxm on 2020-1-10
 * @function:闪屏页
 */
class SplashFragment: BaseImmersionFragment(),Observer<Long> {

    private lateinit var skipBtn: TextView
    private lateinit var splashImageView: ImageView
    private lateinit var mSubscribe: Disposable
    private val defaultTime: Long = 3
    private var mOnSplashListener: OnSplashListener? = null
    private var mIsFinish = false

    private val mTotalTime: Long by lazy {
        arguments?.getLong(SPLASH_TIME)?: defaultTime
    }

    fun setSplashListener(listener: OnSplashListener){
        this.mOnSplashListener = listener
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_splash
    }

    override fun initImmersionBar() {
        hideStatusBar()
    }

    override fun lazyLoad() {
        startCountDown()
    }

    override fun initListener() {
        skipBtn.setOnClickListener {
            mOnSplashListener?.onSplash(0,mTotalTime)
            finish()
        }
    }

    /**
     * 关闭当前页面
     */
    private fun finish(){
        fragmentManager?.let {
            val fragment = it.findFragmentByTag(mTag)
            if(fragment != null){
                it.beginTransaction()
                        .setCustomAnimations(R.anim.slide_right_in,R.anim.slide_left_out)
                        .remove(fragment)
                        .commitAllowingStateLoss()
            }
            mOnSplashListener = null
        }
        mIsFinish = true
        showStatusBar()
    }

    override fun initView(view: View) {
        splashImageView = splash_iv
        skipBtn = splash_skip
        ImageLoaderManager.displayImageWithPlaceholder(Utils.getFullPic(),splashImageView,R.drawable.pic_all)
    }

    @SuppressLint("SetTextI18n")
    override fun onNext(t: Long) {
        skipBtn.background = resources.getDrawable(R.drawable.splash_skip_background)
        skipBtn.text = "跳过${t}s"
        mOnSplashListener?.onSplash(t,mTotalTime)
    }

    override fun onError(e: Throwable) {
        finish()
    }

    override fun onComplete() {
        finish()
    }

    override fun onSubscribe(d: Disposable) {
        mSubscribe = d
    }

    /**
     * 开启倒计时
     */
    private fun startCountDown(){
        Observable.interval(1,TimeUnit.SECONDS)
                .map {
                    mTotalTime - it
                }.take(mTotalTime + 1)
                .compose(IoMainScheduler())
                .subscribe(this)
    }

    /**
     * 关闭倒计时
     */
    private fun stopCountDown(){

        mSubscribe.let {
            if(!mSubscribe.isDisposed) {
                mSubscribe.dispose()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopCountDown()
    }

    companion object{

        const val SPLASH_TIME = "splashTime"

        fun newInstance() = SplashFragment()

        fun newInstance(splashTime: Long) = SplashFragment().apply {
            val bundle = Bundle()
            bundle.putLong(SPLASH_TIME,splashTime)
            this.arguments = bundle
        }
    }

    fun isFinish(): Boolean{
        return mIsFinish
    }

    interface OnSplashListener{
        fun onSplash(time: Long,totalTime: Long)
    }
}