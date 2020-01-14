package com.example.yxm.photogenic.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.example.yxm.photogenic.base.BaseActivity
import com.example.yxm.photogenic.R
import com.example.yxm.photogenic.rxschedulers.IoMainScheduler
import com.gyf.immersionbar.ktx.hideStatusBar
import com.gyf.immersionbar.ktx.immersionBar
import io.reactivex.Observable
import io.reactivex.Observer
import kotlinx.android.synthetic.main.activity_splash.*
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit


/**
 * Created by yxm on 2020-1-10
 * @function:闪屏页
 */
class SplashActivity: BaseActivity(),Observer<Long> {

    private lateinit var skipBtn: Button
    private lateinit var splashImageView: ImageView
    private val mTotalTime: Long = 3
    private lateinit var mSubscribe: Disposable

    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }

    override fun initData() {
        startCountDown()
    }

    override fun initListener() {

        skipBtn.setOnClickListener {
            stopCountDown()
            toMainActivity()
        }
    }

    override fun initView() {
        splashImageView = splash_iv
        skipBtn = splash_skip
    }

    override fun onNext(t: Long) {
        skipBtn.text = "跳过${t}s"
    }

    override fun onError(e: Throwable) {
        toMainActivity()
    }

    override fun onComplete() {
        toMainActivity()
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

    /**
     * 跳到MainActivity
     */
    private fun toMainActivity(){
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun setStatusBarState() {
        immersionBar {
            hideStatusBar()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopCountDown()
    }
}