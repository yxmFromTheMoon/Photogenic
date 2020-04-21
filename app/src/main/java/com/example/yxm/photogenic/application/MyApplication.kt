package com.example.yxm.photogenic.application

import com.mob.MobApplication
import com.yxm.lib_pullalive.AliveJobService

/**
 * Created by yxm on 2020-1-13
 * @function:
 */
class MyApplication: MobApplication() {

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        AliveJobService.start(this)
    }

    companion object {
        @get:Synchronized
        lateinit var instance: MyApplication
    }

}