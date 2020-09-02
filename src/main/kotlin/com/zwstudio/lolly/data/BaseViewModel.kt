package com.zwstudio.lolly.data

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler
import tornadofx.Component
import tornadofx.ScopedInstance

fun <T> Observable<T>.applyIO(): Observable<T> =
    this.subscribeOn(JavaFxScheduler.platform())
        .observeOn(JavaFxScheduler.platform())

open class BaseViewModel: Component(), ScopedInstance {
    val vmSettings: SettingsViewModel by inject()
}
