package com.example.yxm.photogenic.application

import android.app.Application
import android.os.Process

/**
 * Created by yxm on 2020-1-13
 * @function:
 */
class MyApplication: Application() {

    init {
        instance = this
    }

    companion object {
        @get:Synchronized
        lateinit var instance: MyApplication
    }

    override fun onCreate() {
        super.onCreate()
    }

    fun exitApp(){
        Process.killProcess(Process.myPid())
        System.exit(0)
    }
}