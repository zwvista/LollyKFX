package com.zwstudio.lolly.service.wpp

import com.zwstudio.lolly.domain.wpp.MPatternPhrase
import com.zwstudio.lolly.restapi.wpp.RestPatternPhrase
import com.zwstudio.lolly.restapi.wpp.RestPatternWebPage
import com.zwstudio.lolly.service.misc.BaseService
import io.reactivex.rxjava3.core.Observable

class PatternPhraseService: BaseService() {
    fun getDataByPatternId(patternid: Int): Observable<List<MPatternPhrase>> =
        retrofitJson.create(RestPatternPhrase::class.java)
            .getDataByPatternId("PATTERNID,eq,$patternid")
            .map { it.lst!! }

    fun getDataByPatternIdPhraseId(patternid: Int, phraseid: Int): Observable<List<MPatternPhrase>> =
        retrofitJson.create(RestPatternPhrase::class.java)
            .getDataByPatternIdPhraseId("PATTERNID,eq,$patternid", "PHRASEID,eq,$phraseid")
            .map { it.lst!! }

    fun getDataByPhraseId(phraseid: Int): Observable<List<MPatternPhrase>> =
        retrofitJson.create(RestPatternPhrase::class.java)
            .getDataByPhraseId("PHRASEID,eq,$phraseid")
            .map { it.lst!! }

    fun getDataById(id: Int): Observable<List<MPatternPhrase>> =
        retrofitJson.create(RestPatternPhrase::class.java)
            .getDataById("ID,eq,$id")
            .map { it.lst!! }

    fun updateSeqNum(id: Int, seqnum: Int): Observable<Unit> =
        retrofitJson.create(RestPatternWebPage::class.java)
            .updateSeqNum(id, seqnum)
            .map { println(it.toString()) }

    fun update(o: MPatternPhrase): Observable<Unit> =
        retrofitJson.create(RestPatternWebPage::class.java)
            .update(o.id, o.patternid, o.seqnum, o.phraseid)
            .map { println(it.toString()) }

    fun create(o: MPatternPhrase): Observable<Int> =
        retrofitJson.create(RestPatternWebPage::class.java)
            .create(o.patternid, o.seqnum, o.phraseid)
            .doOnNext { println(it.toString()) }

    fun delete(id: Int): Observable<Unit> =
        retrofitJson.create(RestPatternWebPage::class.java)
            .delete(id)
            .map { println(it.toString()) }
}
