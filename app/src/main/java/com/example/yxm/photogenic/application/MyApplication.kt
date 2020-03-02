package com.example.yxm.photogenic.application

import android.os.Process
import com.mob.MobApplication
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout

/**
 * Created by yxm on 2020-1-13
 * @function:
 */
class MyApplication: MobApplication() {

    init {
        instance = this
    }

    companion object {
        @get:Synchronized
        lateinit var instance: MyApplication

        init {
            SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
                ClassicsHeader(context)
            }
            SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout ->
                ClassicsFooter(context)
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
    }

    fun exitApp(){
        Process.killProcess(Process.myPid())
        System.exit(0)
    }
}