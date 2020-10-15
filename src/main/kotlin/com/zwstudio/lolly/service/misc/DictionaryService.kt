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

    fun updateDict(o: MDictionary): Observable<Unit> =
        retrofitJson.create(RestDictionary::class.java)
            .updateDict(o.id, o.langidfrom, o.langidto, o.dictname, o.seqnum, o.dicttypecode, o.url, o.chconv, o.automation, o.template, o.template2)
            .map { println(it.toString()) }

    fun createDict(o: MDictionary): Observable<Int> =
        retrofitJson.create(RestDictionary::class.java)
            .createDict(o.langidfrom, o.langidto, o.dictname, o.seqnum, o.dicttypecode, o.url, o.chconv, o.automation, o.template, o.template2)
            .map {
                println(it.toString())
                it
            }

    fun deleteDict(id: Int): Observable<Unit> =
        retrofitJson.create(RestDictionary::class.java)
            .deleteDict(id)
            .map { println(it.toString()) }

    fun updateSite(o: MDictionary): Observable<Unit> =
        retrofitJson.create(RestDictionary::class.java)
            .updateSite(o.siteid, o.dictname, o.transform, o.wait)
            .map { println(it.toString()) }

    fun createSite(o: MDictionary): Observable<Int> =
        retrofitJson.create(RestDictionary::class.java)
            .createSite(o.dictname, o.transform, o.wait)
            .map {
                println(it.toString())
                it
            }

    fun deleteSite(id: Int): Observable<Unit> =
        retrofitJson.create(RestDictionary::class.java)
            .deleteSite(id)
            .map { println(it.toString()) }
}
