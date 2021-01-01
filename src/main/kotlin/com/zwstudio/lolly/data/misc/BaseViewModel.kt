package com.zwstudio.lolly.data.misc

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler
import tornadofx.*

fun <T> Observable<T>.applyIO(): Observable<T> =
    this.subscribeOn(JavaFxScheduler.platform())
        .observeOn(JavaFxScheduler.platform())

open class BaseViewModel: Component(), ScopedInstance {
    val vmSettings: SettingsViewModel by inject()
}
