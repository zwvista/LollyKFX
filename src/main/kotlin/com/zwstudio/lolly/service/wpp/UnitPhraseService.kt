package com.zwstudio.lolly.service.wpp

import com.zwstudio.lolly.domain.misc.MTextbook
import com.zwstudio.lolly.domain.wpp.MUnitPhrase
import com.zwstudio.lolly.restapi.wpp.RestUnitPhrase
import com.zwstudio.lolly.service.misc.BaseService
import io.reactivex.rxjava3.core.Observable

class UnitPhraseService: BaseService() {
    fun getDataByTextbookUnitPart(textbook: MTextbook, unitPartFrom: Int, unitPartTo: Int): Observable<List<MUnitPhrase>> =
        retrofitJson.create(RestUnitPhrase::class.java)
            .getDataByTextbookUnitPart("TEXTBOOKID,eq,${textbook.id}",
                "UNITPART,bt,$unitPartFrom,$unitPartTo")
            .map {
                val lst = it.lst!!
                for (o in lst)
                    o.textbook = textbook
                lst
            }

    fun getDataByTextbook(textbook: MTextbook): Observable<List<MUnitPhrase>> =
        retrofitJson.create(RestUnitPhrase::class.java)
            .getDataByTextbookUnitPart("TEXTBOOKID,eq,${textbook.id}")
            .map {
                val lst = it.lst!!.distinctBy { it.phraseid }
                for (o in lst)
                    o.textbook = textbook
                lst
            }

    fun getDataByLang(langid: Int, lstTextbooks: List<MTextbook>): Observable<List<MUnitPhrase>> =
        retrofitJson.create(RestUnitPhrase::class.java)
            .getDataByLang("LANGID,eq,$langid")
            .map {
                val lst = it.lst!!
                for (o in lst)
                    o.textbook = lstTextbooks.first { it.id == o.textbookid }
                lst
            }

    fun getDataByLangPhrase(langid: Int, phrase: String, lstTextbooks: List<MTextbook>): Observable<List<MUnitPhrase>> =
        retrofitJson.create(RestUnitPhrase::class.java)
            .getDataByLangPhrase("LANGID,eq,$langid", "PHRASE,eq,$phrase")
            .map {
                val lst = it.lst!!
                for (o in lst)
                    o.textbook = lstTextbooks.first { it.id == o.textbookid }
                lst
            }

    fun updateSeqNum(id: Int, seqnum: Int): Observable<Unit> =
        retrofitJson.create(RestUnitPhrase::class.java)
            .updateSeqNum(id, seqnum)
            .map { println(it.toString()) }

    fun update(o: MUnitPhrase): Observable<Unit> =
        retrofitSP.create(RestUnitPhrase::class.java)
            .update(o.id, o.langid, o.textbookid, o.unit, o.part, o.seqnum, o.phraseid, o.phrase, o.translation)
            .map { println(it.toString()) }

    fun create(o: MUnitPhrase): Observable<Int> =
        retrofitSP.create(RestUnitPhrase::class.java)
            .create(o.id, o.langid, o.textbookid, o.unit, o.part, o.seqnum, o.phraseid, o.phrase, o.translation)
            .map {
                println(it.toString())
                it[0][0].newid!!.toInt()
            }

    fun delete(o: MUnitPhrase): Observable<Unit> =
        retrofitSP.create(RestUnitPhrase::class.java)
            .delete(o.id, o.langid, o.textbookid, o.unit, o.part, o.seqnum, o.phraseid, o.phrase, o.translation)
            .map { println(it.toString()) }
}
