package com.example.yxm.photogenic.rxschedulers

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 *Created by yxm on 2020/1/3
 *@function io模式
 */
class IoMainScheduler<T>: BaseScheduler<T>(Schedulers.io(),AndroidSchedulers.mainThread())