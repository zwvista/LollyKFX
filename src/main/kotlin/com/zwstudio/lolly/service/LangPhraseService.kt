package com.zwstudio.lolly.service

import com.zwstudio.lolly.domain.MLangPhrase
import com.zwstudio.lolly.domain.MTextbook
import com.zwstudio.lolly.restapi.RestLangPhrase
import io.reactivex.rxjava3.core.Observable
import java.net.URLEncoder

class LangPhraseService: BaseService() {
    fun getDataByLang(langid: Int, lstTextbooks: List<MTextbook>): Observable<List<MLangPhrase>> =
        retrofitJson.create(RestLangPhrase::class.java)
            .getDataByLang("LANGID,eq,${langid}")
            .map { it.lst!! }

    fun updateTranslation(id: Int, translation: String?): Observable<Unit> =
        retrofitJson.create(RestLangPhrase::class.java)
            .updateTranslation(id, translation)
            .map { println(it.toString()) }

    fun update(id: Int, langid: Int, phrase: String, translation: String?): Observable<Unit> =
        retrofitJson.create(RestLangPhrase::class.java)
            .update(id, langid, phrase, translation)
            .map { println(it.toString()) }

    fun create(langid: Int, phrase: String, translation: String?): Observable<Int> =
        retrofitJson.create(RestLangPhrase::class.java)
            .create(langid, phrase, translation)
            .doOnNext { println(it.toString()) }

    fun delete(o: MLangPhrase): Observable<Unit> =
        retrofitSP.create(RestLangPhrase::class.java)
            .delete(o.id, o.langid, o.phrase, o.translation)
            .map { println(it.toString()) }
}
