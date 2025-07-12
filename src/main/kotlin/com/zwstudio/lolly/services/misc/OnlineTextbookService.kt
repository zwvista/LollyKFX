package com.zwstudio.lolly.services.misc

import com.zwstudio.lolly.common.retrofitJson
import com.zwstudio.lolly.models.misc.MOnlineTextbook
import com.zwstudio.lolly.restapi.misc.RestOnlineTextbook
import io.reactivex.rxjava3.core.Single
import tornadofx.Component
import tornadofx.ScopedInstance

class OnlineTextbookService: Component(), ScopedInstance {
    private val api = retrofitJson.create(RestOnlineTextbook::class.java)

    fun getDataByLang(langid: Int): Single<List<MOnlineTextbook>> =
        api.getDataByLang("LANGID,eq,$langid")
            .map { it.lst }
}
