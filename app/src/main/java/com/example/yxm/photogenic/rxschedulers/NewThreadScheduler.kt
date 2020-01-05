package com.example.yxm.photogenic.rxschedulers

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 *Created by yxm on 2020/1/3
 *@function newThread模式
 */
class NewThreadScheduler <T>: BaseScheduler<T>(Schedulers.newThread(), AndroidSchedulers.mainThread())