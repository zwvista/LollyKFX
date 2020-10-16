package com.zwstudio.lolly.service.misc

import com.zwstudio.lolly.domain.misc.MCode
import com.zwstudio.lolly.restapi.misc.RestCode
import io.reactivex.rxjava3.core.Observable

class CodeService: BaseService() {
    fun getDictCodes(): Observable<List<MCode>> =
        retrofitJson.create(RestCode::class.java)
            .getDictCodes()
            .map { it.lst!!}
    fun getReadNumberCodes(): Observable<List<MCode>> =
        retrofitJson.create(RestCode::class.java)
            .getReadNumberCodes()
            .map { it.lst!!}
}
