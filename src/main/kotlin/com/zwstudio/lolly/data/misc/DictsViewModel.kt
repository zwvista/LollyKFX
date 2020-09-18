package com.zwstudio.lolly.data.misc

import com.zwstudio.lolly.domain.misc.MDictionary
import com.zwstudio.lolly.service.misc.DictionaryService
import io.reactivex.rxjava3.core.Observable
import javafx.beans.property.SimpleStringProperty
import javafx.collections.ListChangeListener
import tornadofx.asObservable

class DictsViewModel : BaseViewModel() {

    var lstItems = mutableListOf<MDictionary>().asObservable()
    val dictionaryService: DictionaryService by inject()
    val statusText = SimpleStringProperty()

    init {
        lstItems.addListener(ListChangeListener {
            statusText.value = "${lstItems.size} Dictionaries in ${vmSettings.langInfo}"
        })
    }

    fun reload() {
        dictionaryService.getDictsByLang(vmSettings.selectedLang.id)
            .applyIO()
            .subscribe { lstItems.setAll(it) }
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
