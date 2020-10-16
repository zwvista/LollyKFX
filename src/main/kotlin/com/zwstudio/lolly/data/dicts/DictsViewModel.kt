package com.zwstudio.lolly.data.dicts

import com.zwstudio.lolly.data.misc.BaseViewModel
import com.zwstudio.lolly.data.misc.applyIO
import com.zwstudio.lolly.domain.misc.MDictionary
import com.zwstudio.lolly.service.misc.DictionaryService
import io.reactivex.rxjava3.core.Observable
import javafx.beans.property.SimpleStringProperty
import javafx.collections.ListChangeListener
import tornadofx.*

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

    fun newDictionary() = MDictionary().apply {
        langidfrom = vmSettings.selectedLang.id
    }

    fun updateDict(o: MDictionary): Observable<Unit> =
        dictionaryService.updateDict(o)
        .applyIO()

    fun createDict(o: MDictionary): Observable<Int> =
        dictionaryService.createDict(o)
        .applyIO()

    fun deleteDict(id: Int): Observable<Unit> =
        dictionaryService.deleteDict(id)
        .applyIO()

    fun updateSite(o: MDictionary): Observable<Unit> =
        dictionaryService.updateSite(o)
        .applyIO()

    fun createSite(o: MDictionary): Observable<Int> =
        dictionaryService.createSite(o)
        .applyIO()

    fun deleteSite(id: Int): Observable<Unit> =
        dictionaryService.deleteSite(id)
        .applyIO()
}
