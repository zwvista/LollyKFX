package com.zwstudio.lolly.service.misc

import com.zwstudio.lolly.domain.misc.MAutoCorrect
import com.zwstudio.lolly.restapi.misc.RestAutoCorrect
import io.reactivex.rxjava3.core.Observable

class AutoCorrectService: BaseService() {
    fun getDataByLang(langid: Int): Observable<List<MAutoCorrect>> =
        retrofitJson.create(RestAutoCorrect::class.java)
            .getDataByLang("LANGID,eq,$langid")
            .map { it.lst!! }
}
