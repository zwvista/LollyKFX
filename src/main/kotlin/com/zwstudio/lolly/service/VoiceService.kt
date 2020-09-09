package com.zwstudio.lolly.service

import com.zwstudio.lolly.domain.misc.MVoice
import com.zwstudio.lolly.restapi.RestVoice
import io.reactivex.rxjava3.core.Observable

class VoiceService: BaseService() {
    fun getDataByLang(langid: Int): Observable<List<MVoice>> =
        retrofitJson.create(RestVoice::class.java)
            .getDataByLang("LANGID,eq,$langid")
            .map { it.lst!! }
}
