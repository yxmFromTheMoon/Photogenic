package com.example.yxm.photogenic.application

import com.mob.MobApplication

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
    }

}