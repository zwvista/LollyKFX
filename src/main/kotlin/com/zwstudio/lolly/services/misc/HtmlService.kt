package com.zwstudio.lolly.services.misc

import com.zwstudio.lolly.viewmodels.misc.applyIO
import com.zwstudio.lolly.restapi.misc.RestHtml
import io.reactivex.rxjava3.core.Single

class HtmlService: BaseService() {

    // https://futurestud.io/tutorials/retrofit-2-receive-plain-string-responses
    fun getHtml(url: String): Single<String> =
        retrofitHtml.create(RestHtml::class.java)
            .getStringResponse(url)
            .applyIO()
}
