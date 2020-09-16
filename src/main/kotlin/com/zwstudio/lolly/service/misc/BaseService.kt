package com.zwstudio.lolly.service.misc

import com.zwstudio.lolly.app.LollyApp
import retrofit2.Retrofit
import tornadofx.Component
import tornadofx.ScopedInstance

open class BaseService: Component(), ScopedInstance {
    val retrofitJson: Retrofit
        get() = LollyApp.retrofitJson
    val retrofitSP: Retrofit
        get() = LollyApp.retrofitSP
    val retrofitHtml: Retrofit
        get() = LollyApp.retrofitHtml
}
