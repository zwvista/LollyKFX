package com.zwstudio.lolly.data.misc

import com.zwstudio.lolly.domain.misc.MDictionary
import com.zwstudio.lolly.service.misc.DictionaryService
import io.reactivex.rxjava3.core.Observable

class DictsViewModel : BaseViewModel() {

    var lstItems = mutableListOf<MDictionary>()
    val dictionaryService: DictionaryService by inject()

    init {
    }

    fun reload() {
        dictionaryService.getDictsByLang(vmSettings.selectedLang.id)
            .map { lstItems = it.toMutableList() }
            .applyIO()
            .subscribe()
    }

    fun update(o: MDictionary): Observable<Unit> =
        dictionaryService.update(o)
        .applyIO()

    fun create(o: MDictionary): Observable<Int> =
        dictionaryService.create(o)
        .applyIO()

    fun delete(id: Int): Observable<Unit> =
        dictionaryService.delete(id)
        .applyIO()
}
