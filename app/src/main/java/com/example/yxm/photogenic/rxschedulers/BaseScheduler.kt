package com.example.yxm.photogenic.rxschedulers

import io.reactivex.*
import org.reactivestreams.Publisher

/**
 *Created by yxm on 2020/1/3
 *@function rx基类
 */
abstract class BaseScheduler<T> protected constructor(private val subscribeScheduler: Scheduler,
                                                      private val observableScheduler: Scheduler):
        ObservableTransformer<T,T>,
        MaybeTransformer<T,T>,
        FlowableTransformer<T,T>,
        CompletableTransformer,
        SingleTransformer<T,T>{

    override fun apply(upstream: Completable): CompletableSource {
        return upstream.subscribeOn(subscribeScheduler)
                .observeOn(observableScheduler)
    }

    override fun apply(upstream: Flowable<T>): Publisher<T> {
        return upstream.subscribeOn(subscribeScheduler)
                .observeOn(observableScheduler)
    }

    override fun apply(upstream: Maybe<T>): MaybeSource<T> {
        return upstream.subscribeOn(subscribeScheduler)
                .observeOn(observableScheduler)
    }

    override fun apply(upstream: Observable<T>): ObservableSource<T> {
        return upstream.subscribeOn(subscribeScheduler)
                .observeOn(observableScheduler)
    }

    override fun apply(upstream: Single<T>): SingleSource<T> {
        return upstream.subscribeOn(subscribeScheduler)
                .observeOn(observableScheduler)
    }
}