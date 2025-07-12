package com.zwstudio.lolly.services.misc

import com.zwstudio.lolly.common.completeDelete
import com.zwstudio.lolly.common.completeUpdate
import com.zwstudio.lolly.common.debugCreate
import com.zwstudio.lolly.common.retrofitJson
import com.zwstudio.lolly.models.misc.MDictionary
import com.zwstudio.lolly.restapi.misc.RestDictionary
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import tornadofx.Component
import tornadofx.ScopedInstance

class DictionaryService: Component(), ScopedInstance {
    private val api = retrofitJson.create(RestDictionary::class.java)

    fun getDictsByLang(langid: Int): Single<List<MDictionary>> =
        api.getDictsByLang("LANGIDFROM,eq,$langid")
            .map { it.lst }

    fun getDictsReferenceByLang(langid: Int): Single<List<MDictionary>> =
        api.getDictsReferenceByLang("LANGIDFROM,eq,$langid")
            .map { it.lst }

    fun getDictsNoteByLang(langid: Int): Single<List<MDictionary>> =
        api.getDictsNoteByLang("LANGIDFROM,eq,$langid")
            .map { it.lst }

    fun getDictsTranslationByLang(langid: Int): Single<List<MDictionary>> =
        api.getDictsTranslationByLang("LANGIDFROM,eq,$langid")
            .map { it.lst }

    fun update(item: MDictionary): Completable =
        api.update(item.id, item.langidfrom, item.langidto, item.dictname, item.seqnum, item.dicttypecode, item.url, item.chconv, item.automation, item.template, item.template2)
            .completeUpdate(item.id)

    fun create(item: MDictionary): Single<Int> =
        api.create(item.langidfrom, item.langidto, item.dictname, item.seqnum, item.dicttypecode, item.url, item.chconv, item.automation, item.template, item.template2)
            .debugCreate()

    fun delete(id: Int): Completable =
        api.delete(id).completeDelete()
}
