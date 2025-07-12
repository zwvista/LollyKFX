package com.zwstudio.lolly.services.misc

import com.zwstudio.lolly.common.retrofitJson
import com.zwstudio.lolly.models.misc.MAutoCorrect
import com.zwstudio.lolly.restapi.misc.RestAutoCorrect
import io.reactivex.rxjava3.core.Single
import tornadofx.Component
import tornadofx.ScopedInstance

class AutoCorrectService: Component(), ScopedInstance {
    fun getDataByLang(langid: Int): Single<List<MAutoCorrect>> =
        retrofitJson.create(RestAutoCorrect::class.java)
            .getDataByLang("LANGID,eq,$langid")
            .map { it.lst }
}
