package com.zwstudio.lolly.services.misc

import com.zwstudio.lolly.models.misc.MCode
import com.zwstudio.lolly.restapi.misc.RestCode
import io.reactivex.rxjava3.core.Single

class CodeService: BaseService() {
    fun getDictCodes(): Single<List<MCode>> =
        retrofitJson.create(RestCode::class.java)
            .getDictCodes()
            .map { it.lst!!}
    fun getReadNumberCodes(): Single<List<MCode>> =
        retrofitJson.create(RestCode::class.java)
            .getReadNumberCodes()
            .map { it.lst!!}
}
