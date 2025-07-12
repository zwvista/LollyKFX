package com.zwstudio.lolly.services.misc

import com.zwstudio.lolly.common.retrofitJson
import com.zwstudio.lolly.models.misc.MOnlineTextbook
import com.zwstudio.lolly.restapi.misc.RestOnlineTextbook
import io.reactivex.rxjava3.core.Single
import tornadofx.Component
import tornadofx.ScopedInstance

class OnlineTextbookService: Component(), ScopedInstance {
    fun getDataByLang(langid: Int): Single<List<MOnlineTextbook>> =
        retrofitJson.create(RestOnlineTextbook::class.java)
            .getDataByLang("LANGID,eq,$langid")
            .map { it.lst }
}
