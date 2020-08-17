package com.zwstudio.lolly.service

import com.zwstudio.lolly.app.LollyApp
import retrofit2.Retrofit

open class BaseService {
    val retrofitJson: Retrofit
        get() = LollyApp.retrofitJson
    val retrofitSP: Retrofit
        get() = LollyApp.retrofitSP
    val retrofitHtml: Retrofit
        get() = LollyApp.retrofitHtml
}
