package com.zwstudio.lolly.services.wpp

import com.zwstudio.lolly.models.wpp.MLangPhrase
import com.zwstudio.lolly.models.wpp.MLangWord
import com.zwstudio.lolly.models.wpp.MWordPhrase
import com.zwstudio.lolly.restapi.wpp.RestWordPhrase
import com.zwstudio.lolly.services.misc.BaseService
import io.reactivex.rxjava3.core.Observable

class WordPhraseService: BaseService() {
    fun getDataByWordPhrase(wordid: Int, phraseid: Int): Observable<List<MWordPhrase>> =
        retrofitJson.create(RestWordPhrase::class.java)
            .getDataByWordPhrase("WORDID,eq,$wordid", "PHRASEID,eq,$phraseid")
            .map { it.lst!! }

    fun getPhrasesByWordId(wordid: Int): Observable<List<MLangPhrase>> =
        retrofitJson.create(RestWordPhrase::class.java)
            .getPhrasesByWordId("WORDID,eq,$wordid")
            .map { it.lst!! }

    fun getWordsByPhraseId(phraseid: Int): Observable<List<MLangWord>> =
        retrofitJson.create(RestWordPhrase::class.java)
            .getWordsByPhraseId("PHRASEID,eq,$phraseid")
            .map { it.lst!! }

    fun create(wordid: Int, phraseid: Int): Observable<Int> =
        retrofitJson.create(RestWordPhrase::class.java)
            .create(wordid, phraseid)
            .doOnNext { println(it.toString()) }

    fun delete(id: Int): Observable<Unit> =
        retrofitJson.create(RestWordPhrase::class.java)
            .delete(id)
            .map { println(it.toString()) }

    fun link(wordid: Int, phraseid: Int) =
        create(wordid, phraseid)

    fun unlink(wordid: Int, phraseid: Int): Observable<Unit> =
        getDataByWordPhrase(wordid, phraseid).map {
            it.map { delete(it.id) }
        }
}
