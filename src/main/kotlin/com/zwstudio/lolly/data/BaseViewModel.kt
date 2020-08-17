package com.zwstudio.lolly.data

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler
import tornadofx.Component
import tornadofx.ScopedInstance

fun <T> Observable<T>.applyIO(): Observable<T> =
    this.subscribeOn(Schedulers.computation())
        .observeOn(JavaFxScheduler.platform())

open class BaseViewModel2: Component(), ScopedInstance {
    val vmSettings: SettingsViewModel by inject()
}
