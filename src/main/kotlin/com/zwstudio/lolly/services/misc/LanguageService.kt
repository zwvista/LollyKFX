package com.zwstudio.lolly.services.misc

import com.zwstudio.lolly.models.misc.MLanguage
import com.zwstudio.lolly.restapi.misc.RestLanguage
import io.reactivex.rxjava3.core.Observable

class LanguageService: BaseService() {
    fun getData(): Observable<List<MLanguage>> =
        retrofitJson.create(RestLanguage::class.java)
            .getData()
            .map { it.lst!!}
}
