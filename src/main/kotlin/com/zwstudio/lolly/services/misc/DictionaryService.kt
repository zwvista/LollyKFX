package com.zwstudio.lolly.services.misc

import com.zwstudio.lolly.models.misc.MDictionary
import com.zwstudio.lolly.restapi.misc.RestDictionary
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class DictionaryService: BaseService() {
    fun getDictsByLang(langid: Int): Single<List<MDictionary>> =
        retrofitJson.create(RestDictionary::class.java)
            .getDictsByLang("LANGIDFROM,eq,$langid")
            .map { it.lst }

    fun getDictsReferenceByLang(langid: Int): Single<List<MDictionary>> =
        retrofitJson.create(RestDictionary::class.java)
            .getDictsReferenceByLang("LANGIDFROM,eq,$langid")
            .map { it.lst }

    fun getDictsNoteByLang(langid: Int): Single<List<MDictionary>> =
        retrofitJson.create(RestDictionary::class.java)
            .getDictsNoteByLang("LANGIDFROM,eq,$langid")
            .map { it.lst }

    fun getDictsTranslationByLang(langid: Int): Single<List<MDictionary>> =
        retrofitJson.create(RestDictionary::class.java)
            .getDictsTranslationByLang("LANGIDFROM,eq,$langid")
            .map { it.lst }

    fun update(o: MDictionary): Completable =
        retrofitJson.create(RestDictionary::class.java)
            .update(o.id, o.langidfrom, o.langidto, o.dictname, o.seqnum, o.dicttypecode, o.url, o.chconv, o.automation, o.template, o.template2)
            .flatMapCompletable { println(it.toString()); Completable.complete() }

    fun create(o: MDictionary): Single<Int> =
        retrofitJson.create(RestDictionary::class.java)
            .create(o.langidfrom, o.langidto, o.dictname, o.seqnum, o.dicttypecode, o.url, o.chconv, o.automation, o.template, o.template2)
            .map {
                println(it.toString())
                it
            }

    fun delete(id: Int): Completable =
        retrofitJson.create(RestDictionary::class.java)
            .delete(id)
            .flatMapCompletable { println(it.toString()); Completable.complete() }
}
