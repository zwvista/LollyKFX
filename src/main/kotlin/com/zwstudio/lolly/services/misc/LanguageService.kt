package com.zwstudio.lolly.services.misc

import com.zwstudio.lolly.common.retrofitJson
import com.zwstudio.lolly.models.misc.MLanguage
import com.zwstudio.lolly.restapi.misc.RestLanguage
import io.reactivex.rxjava3.core.Single
import tornadofx.Component
import tornadofx.ScopedInstance

class LanguageService: Component(), ScopedInstance {
    private val api = retrofitJson.create(RestLanguage::class.java)

    fun getData(): Single<List<MLanguage>> =
        api.getData()
            .map { it.lst }
}
