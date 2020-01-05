package com.example.yxm.photogenic.rxschedulers

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 *Created by yxm on 2020/1/3
 *@function single模式
 */
class SingleScheduler <T>: BaseScheduler<T>(Schedulers.single(), AndroidSchedulers.mainThread())