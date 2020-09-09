package com.zwstudio.lolly.service

import com.zwstudio.lolly.domain.misc.MTextbook
import com.zwstudio.lolly.domain.wpp.MLangWord
import com.zwstudio.lolly.restapi.RestLangWord
import io.reactivex.rxjava3.core.Observable

class LangWordService: BaseService() {
    fun getDataByLang(langid: Int, lstTextbooks: List<MTextbook>): Observable<List<MLangWord>> =
        retrofitJson.create(RestLangWord::class.java)
            .getDataByLang("LANGID,eq,${langid}")
            .map { it.lst!! }

    fun updateNote(id: Int, note: String?): Observable<Unit> =
        retrofitJson.create(RestLangWord::class.java)
            .updateNote(id, note)
            .map { println(it.toString()) }

    fun update(id: Int, langid: Int, word: String, note: String?): Observable<Unit> =
        retrofitJson.create(RestLangWord::class.java)
            .update(id, langid, word, note)
            .map { println(it.toString()) }

    fun create(langid: Int, word: String, note: String?): Observable<Int> =
        retrofitJson.create(RestLangWord::class.java)
            .create(langid, word, note)
            .doOnNext { println(it.toString()) }

    fun delete(o: MLangWord): Observable<Unit> =
        retrofitJson.create(RestLangWord::class.java)
            .delete(o.id, o.langid, o.word, o.note, o.famiid, o.correct, o.total)
            .map { println(it.toString()) }
}
