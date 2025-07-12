package com.zwstudio.lolly.services.wpp

import com.zwstudio.lolly.models.wpp.MLangPhrase
import com.zwstudio.lolly.models.wpp.MLangWord
import com.zwstudio.lolly.models.wpp.MWordPhrase
import com.zwstudio.lolly.restapi.wpp.RestWordPhrase
import com.zwstudio.lolly.services.misc.BaseService
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class WordPhraseService: BaseService() {
    fun getDataByWordPhrase(wordid: Int, phraseid: Int): Single<List<MWordPhrase>> =
        retrofitJson.create(RestWordPhrase::class.java)
            .getDataByWordPhrase("WORDID,eq,$wordid", "PHRASEID,eq,$phraseid")
            .map { it.lst }

    fun getPhrasesByWordId(wordid: Int): Single<List<MLangPhrase>> =
        retrofitJson.create(RestWordPhrase::class.java)
            .getPhrasesByWordId("WORDID,eq,$wordid")
            .map { it.lst }

    fun getWordsByPhraseId(phraseid: Int): Single<List<MLangWord>> =
        retrofitJson.create(RestWordPhrase::class.java)
            .getWordsByPhraseId("PHRASEID,eq,$phraseid")
            .map { it.lst }

    fun create(wordid: Int, phraseid: Int): Single<Int> =
        retrofitJson.create(RestWordPhrase::class.java)
            .create(wordid, phraseid)
            .doAfterSuccess { println(it.toString()) }

    fun delete(id: Int): Completable =
        retrofitJson.create(RestWordPhrase::class.java)
            .delete(id)
            .flatMapCompletable { println(it.toString()); Completable.complete() }

    fun link(wordid: Int, phraseid: Int) =
        create(wordid, phraseid)

    fun unlink(wordid: Int, phraseid: Int): Completable =
        getDataByWordPhrase(wordid, phraseid).flatMapCompletable {
            it.map { delete(it.id) }; Completable.complete()
        }
}
