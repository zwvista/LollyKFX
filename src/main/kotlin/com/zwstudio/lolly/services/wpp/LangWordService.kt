package com.zwstudio.lolly.services.wpp

import com.zwstudio.lolly.models.wpp.MLangWord
import com.zwstudio.lolly.restapi.wpp.RestLangWord
import com.zwstudio.lolly.services.misc.BaseService
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class LangWordService: BaseService() {
    fun getDataByLang(langid: Int): Single<List<MLangWord>> =
        retrofitJson.create(RestLangWord::class.java)
            .getDataByLang("LANGID,eq,$langid")
            .map { it.lst!! }

    fun updateNote(id: Int, note: String): Completable =
        retrofitJson.create(RestLangWord::class.java)
            .updateNote(id, note)
            .flatMapCompletable { println(it.toString()); Completable.complete() }

    fun update(id: Int, langid: Int, word: String, note: String): Completable =
        retrofitJson.create(RestLangWord::class.java)
            .update(id, langid, word, note)
            .flatMapCompletable { println(it.toString()); Completable.complete() }

    fun create(langid: Int, word: String, note: String): Single<Int> =
        retrofitJson.create(RestLangWord::class.java)
            .create(langid, word, note)
            .doAfterSuccess { println(it.toString()) }

    fun delete(o: MLangWord): Completable =
        retrofitJson.create(RestLangWord::class.java)
            .delete(o.id, o.langid, o.word, o.note, o.famiid, o.correct, o.total)
            .flatMapCompletable { println(it.toString()); Completable.complete() }
}
