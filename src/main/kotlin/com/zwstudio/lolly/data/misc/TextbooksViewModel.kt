package com.zwstudio.lolly.data.misc

import com.zwstudio.lolly.domain.misc.MTextbook
import com.zwstudio.lolly.service.misc.TextbookService
import io.reactivex.rxjava3.core.Observable
import javafx.beans.property.SimpleStringProperty
import javafx.collections.ListChangeListener
import tornadofx.asObservable

class TextbooksViewModel : BaseViewModel() {

    var lstItems = mutableListOf<MTextbook>().asObservable()
    val textbookService: TextbookService by inject()
    val statusText = SimpleStringProperty()

    init {
        lstItems.addListener(ListChangeListener {
            statusText.value = "${lstItems.size} Textbooks in ${vmSettings.langInfo}"
        })
    }

    fun reload() {
        textbookService.getDataByLang(vmSettings.selectedLang.id)
            .applyIO()
            .subscribe { lstItems.setAll(it) }
    }

    fun update(o: MTextbook): Observable<Unit> =
        textbookService.update(o)
        .applyIO()

    fun create(o: MTextbook): Observable<Int> =
        textbookService.create(o)
        .applyIO()

    fun delete(id: Int): Observable<Unit> =
        textbookService.delete(id)
        .applyIO()
}
