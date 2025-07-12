package com.zwstudio.lolly.services.wpp

import com.zwstudio.lolly.common.*
import com.zwstudio.lolly.models.misc.MTextbook
import com.zwstudio.lolly.models.wpp.MUnitPhrase
import com.zwstudio.lolly.restapi.wpp.RestUnitPhrase
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import tornadofx.Component
import tornadofx.ScopedInstance

class UnitPhraseService: Component(), ScopedInstance {
    private val api = retrofitJson.create(RestUnitPhrase::class.java)
    private val apiSP = retrofitSP.create(RestUnitPhrase::class.java)

    fun getDataByTextbookUnitPart(textbook: MTextbook, unitPartFrom: Int, unitPartTo: Int): Single<List<MUnitPhrase>> =
        api.getDataByTextbookUnitPart("TEXTBOOKID,eq,${textbook.id}",
            "UNITPART,bt,$unitPartFrom,$unitPartTo")
            .map {
                val lst = it.lst
                for (o in lst)
                    o.textbook = textbook
                lst
            }

    fun getDataByTextbook(textbook: MTextbook): Single<List<MUnitPhrase>> =
        api.getDataByTextbook("TEXTBOOKID,eq,${textbook.id}")
            .map {
                val lst = it.lst.distinctBy { it.phraseid }
                for (o in lst)
                    o.textbook = textbook
                lst
            }

    fun getDataByLang(langid: Int, lstTextbooks: List<MTextbook>): Single<List<MUnitPhrase>> =
        api.getDataByLang("LANGID,eq,$langid")
            .map {
                val lst = it.lst

                for (o in lst)
                    o.textbook = lstTextbooks.first { it.id == o.textbookid }
                lst
            }

    fun getDataByLangPhrase(langid: Int, phrase: String, lstTextbooks: List<MTextbook>): Single<List<MUnitPhrase>> =
        api.getDataByLangPhrase("LANGID,eq,$langid", "PHRASE,eq,$phrase")
            .map {
                val lst = it.lst

                for (o in lst)
                    o.textbook = lstTextbooks.first { it.id == o.textbookid }
                lst
            }

    fun updateSeqNum(id: Int, seqnum: Int): Completable =
        api.updateSeqNum(id, seqnum).completeUpdate(id)

    fun update(item: MUnitPhrase): Completable =
        apiSP.update(item.id, item.langid, item.textbookid, item.unit, item.part, item.seqnum, item.phraseid, item.phrase, item.translation)
            .completeUpdateResult(item.id)

    fun create(item: MUnitPhrase): Single<Int> =
        apiSP.create(item.id, item.langid, item.textbookid, item.unit, item.part, item.seqnum, item.phraseid, item.phrase, item.translation)
            .debugCreateResult()

    fun delete(item: MUnitPhrase): Completable =
        apiSP.delete(item.id, item.langid, item.textbookid, item.unit, item.part, item.seqnum, item.phraseid, item.phrase, item.translation)
            .completeDeleteResult()
}
