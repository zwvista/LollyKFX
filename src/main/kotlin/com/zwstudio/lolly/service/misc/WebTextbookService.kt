package com.zwstudio.lolly.service.misc

import com.zwstudio.lolly.domain.misc.MSelectItem
import com.zwstudio.lolly.domain.misc.MTextbook
import com.zwstudio.lolly.domain.misc.MWebTextbook
import com.zwstudio.lolly.restapi.misc.RestTextbook
import com.zwstudio.lolly.restapi.misc.RestWebTextbook
import io.reactivex.rxjava3.core.Observable

class WebTextbookService: BaseService() {
    fun getDataByLang(langid: Int): Observable<List<MWebTextbook>> =
        retrofitJson.create(RestWebTextbook::class.java)
            .getDataByLang("LANGID,eq,$langid")
            .map {
                it.lst!!
            }
}
