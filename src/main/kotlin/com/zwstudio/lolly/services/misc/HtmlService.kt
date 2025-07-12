package com.zwstudio.lolly.services.misc

import com.zwstudio.lolly.common.retrofitHtml
import com.zwstudio.lolly.restapi.misc.RestHtml
import com.zwstudio.lolly.viewmodels.misc.applyIO
import io.reactivex.rxjava3.core.Single
import tornadofx.Component
import tornadofx.ScopedInstance

class HtmlService: Component(), ScopedInstance {
    private val apiHtml = retrofitHtml.create(RestHtml::class.java)

    // https://futurestud.io/tutorials/retrofit-2-receive-plain-string-responses
    fun getHtml(url: String): Single<String> =
        apiHtml.getStringResponse(url)
            .applyIO()
}
