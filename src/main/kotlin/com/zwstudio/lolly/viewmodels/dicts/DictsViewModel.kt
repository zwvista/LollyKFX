package com.zwstudio.lolly.viewmodels.dicts

import com.zwstudio.lolly.viewmodels.misc.BaseViewModel
import com.zwstudio.lolly.viewmodels.misc.applyIO
import com.zwstudio.lolly.models.misc.MDictionary
import com.zwstudio.lolly.services.misc.DictionaryService
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
        langnamefrom = vmSettings.selectedLang.langname!!
        dicttypecode = 3
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
