package com.zwstudio.lolly.viewmodels.misc

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler
import tornadofx.Component
import tornadofx.ScopedInstance

fun <T: Any> Observable<T>.applyIO(): Observable<T> =
    this.subscribeOn(JavaFxScheduler.platform())
        .observeOn(JavaFxScheduler.platform())

fun <T: Any> Single<T>.applyIO(): Single<T> =
    this.subscribeOn(JavaFxScheduler.platform())
        .observeOn(JavaFxScheduler.platform())

fun Completable.applyIO(): Completable =
    this.subscribeOn(JavaFxScheduler.platform())
        .observeOn(JavaFxScheduler.platform())

open class BaseViewModel: Component(), ScopedInstance {
    val vmSettings: SettingsViewModel by inject()
}
