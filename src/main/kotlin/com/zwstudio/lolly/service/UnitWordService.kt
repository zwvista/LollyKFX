package com.zwstudio.lolly.service

import com.zwstudio.lolly.domain.MTextbook
import com.zwstudio.lolly.domain.wpp.MUnitWord
import com.zwstudio.lolly.restapi.RestUnitWord
import io.reactivex.rxjava3.core.Observable

class UnitWordService: BaseService() {
    fun getDataByTextbookUnitPart(textbook: MTextbook, unitPartFrom: Int, unitPartTo: Int): Observable<List<MUnitWord>> =
        retrofitJson.create(RestUnitWord::class.java)
            .getDataByTextbookUnitPart("TEXTBOOKID,eq,${textbook.id}",
                "UNITPART,bt,${unitPartFrom},${unitPartTo}")
            .map {
                val lst = it.lst!!
                for (o in lst)
                    o.textbook = textbook
                lst
            }

    fun getDataByLang(langid: Int, lstTextbooks: List<MTextbook>): Observable<List<MUnitWord>> =
        retrofitJson.create(RestUnitWord::class.java)
            .getDataByLang("LANGID,eq,${langid}")
            .map {
                val lst = it.lst!!
                for (o in lst)
                    o.textbook = lstTextbooks.first { it.id == o.textbookid }
                lst
            }

    fun getDataByLangWord(wordid: Int): Observable<List<MUnitWord>> =
        retrofitJson.create(RestUnitWord::class.java)
            .getDataByLangWord("WORDID,eq,$wordid")
            .map { it.lst!! }

    fun updateSeqNum(id: Int, seqnum: Int): Observable<Unit> =
        retrofitJson.create(RestUnitWord::class.java)
            .updateSeqNum(id, seqnum)
            .map { println(it.toString()) }

    fun updateNote(id: Int, note: String?): Observable<Unit> =
        retrofitJson.create(RestUnitWord::class.java)
            .updateNote(id, note)
            .map { println(it.toString()) }

    fun update(o: MUnitWord): Observable<Unit> =
        retrofitSP.create(RestUnitWord::class.java)
            .update(o.id, o.langid, o.textbookid, o.unit, o.part, o.seqnum, o.wordid, o.word, o.note, o.famiid, o.level, o.correct, o.total)
            .map { println(it.toString()) }

    fun create(o: MUnitWord): Observable<Int> =
        retrofitSP.create(RestUnitWord::class.java)
            .create(o.id, o.langid, o.textbookid, o.unit, o.part, o.seqnum, o.wordid, o.word, o.note, o.famiid, o.level, o.correct, o.total)
            .map {
                println(it.toString())
                it[0][0].newid!!.toInt()
            }

    fun delete(o: MUnitWord): Observable<Unit> =
        retrofitSP.create(RestUnitWord::class.java)
            .delete(o.id, o.langid, o.textbookid, o.unit, o.part, o.seqnum, o.wordid, o.word, o.note, o.famiid, o.level, o.correct, o.total)
            .map { println(it.toString()) }
}
