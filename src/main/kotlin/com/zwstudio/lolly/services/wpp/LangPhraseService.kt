package com.zwstudio.lolly.services.wpp

import com.zwstudio.lolly.models.wpp.MLangPhrase
import com.zwstudio.lolly.restapi.wpp.RestLangPhrase
import com.zwstudio.lolly.services.misc.BaseService
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class LangPhraseService: BaseService() {
    fun getDataByLang(langid: Int): Single<List<MLangPhrase>> =
        retrofitJson.create(RestLangPhrase::class.java)
            .getDataByLang("LANGID,eq,$langid")
            .map { it.lst!! }

    fun updateTranslation(id: Int, translation: String): Completable =
        retrofitJson.create(RestLangPhrase::class.java)
            .updateTranslation(id, translation)
            .flatMapCompletable { println(it.toString()); Completable.complete() }

    fun update(id: Int, langid: Int, phrase: String, translation: String): Completable =
        retrofitJson.create(RestLangPhrase::class.java)
            .update(id, langid, phrase, translation)
            .flatMapCompletable { println(it.toString()); Completable.complete() }

    fun create(langid: Int, phrase: String, translation: String): Single<Int> =
        retrofitJson.create(RestLangPhrase::class.java)
            .create(langid, phrase, translation)
            .doAfterSuccess { println(it.toString()) }

    fun delete(o: MLangPhrase): Completable =
        retrofitSP.create(RestLangPhrase::class.java)
            .delete(o.id, o.langid, o.phrase, o.translation)
            .flatMapCompletable { println(it.toString()); Completable.complete() }
}
