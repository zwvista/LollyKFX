package com.zwstudio.lolly.services.misc

import com.zwstudio.lolly.common.retrofitJson
import com.zwstudio.lolly.models.misc.MCode
import com.zwstudio.lolly.restapi.misc.RestCode
import io.reactivex.rxjava3.core.Single
import tornadofx.Component
import tornadofx.ScopedInstance

class CodeService: Component(), ScopedInstance {
    private val api = retrofitJson.create(RestCode::class.java)

    fun getDictCodes(): Single<List<MCode>> =
        api.getDictCodes()
            .map { it.lst }
    fun getReadNumberCodes(): Single<List<MCode>> =
        api.getReadNumberCodes()
            .map { it.lst }
}
