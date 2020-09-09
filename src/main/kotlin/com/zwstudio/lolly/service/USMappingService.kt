package com.zwstudio.lolly.service

import com.zwstudio.lolly.domain.misc.MUSMapping
import com.zwstudio.lolly.restapi.RestUSMapping
import io.reactivex.rxjava3.core.Observable

class USMappingService: BaseService() {
    fun getData(): Observable<List<MUSMapping>> =
        retrofitJson.create(RestUSMapping::class.java)
            .getData()
            .map { it.lst!! }
}
