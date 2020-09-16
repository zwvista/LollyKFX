package com.zwstudio.lolly.service.misc

import com.zwstudio.lolly.domain.misc.MDictionary
import com.zwstudio.lolly.restapi.misc.RestDictionary
import io.reactivex.rxjava3.core.Observable

class DictionaryService: BaseService() {
    fun getDictsByLang(langid: Int): Observable<List<MDictionary>> =
        retrofitJson.create(RestDictionary::class.java)
            .getDictsByLang("LANGIDFROM,eq,$langid")
            .map { it.lst!! }

    fun getDictsReferenceByLang(langid: Int): Observable<List<MDictionary>> =
        retrofitJson.create(RestDictionary::class.java)
            .getDictsReferenceByLang("LANGIDFROM,eq,$langid")
            .map { it.lst!! }

    fun getDictsNoteByLang(langid: Int): Observable<List<MDictionary>> =
        retrofitJson.create(RestDictionary::class.java)
            .getDictsNoteByLang("LANGIDFROM,eq,$langid")
            .map { it.lst!! }

    fun getDictsTranslationByLang(langid: Int): Observable<List<MDictionary>> =
        retrofitJson.create(RestDictionary::class.java)
            .getDictsTranslationByLang("LANGIDFROM,eq,$langid")
            .map { it.lst!! }

    fun update(o: MDictionary): Observable<Unit> =
        retrofitSP.create(RestDictionary::class.java)
            .update(o.id, o.dictid, o.langidfrom, o.langidto, o.seqnum, o.dicttypeid, o.dictname, o.url, o.chconv, o.automation, o.transform, o.wait, o.template, o.template2)
            .map { println(it.toString()) }

    fun create(o: MDictionary): Observable<Int> =
        retrofitSP.create(RestDictionary::class.java)
            .create(o.dictid, o.langidfrom, o.langidto, o.seqnum, o.dicttypeid, o.dictname, o.url, o.chconv, o.automation, o.transform, o.wait, o.template, o.template2)
            .map {
                println(it.toString())
                it
            }

    fun delete(id: Int): Observable<Unit> =
        retrofitSP.create(RestDictionary::class.java)
            .delete(id)
            .map { println(it.toString()) }
}
