package com.example.yxm.photogenic.rxschedulers

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 *Created by yxm on 2020/1/3
 *@function computation模式
 */
class ComputationScheduler<T>: BaseScheduler<T>(Schedulers.computation(), AndroidSchedulers.mainThread())