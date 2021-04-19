package com.zwstudio.lolly.viewmodels.textbooks

import com.zwstudio.lolly.models.misc.MTextbook
import com.zwstudio.lolly.services.misc.TextbookService
import com.zwstudio.lolly.viewmodels.misc.BaseViewModel
import com.zwstudio.lolly.viewmodels.misc.applyIO
import io.reactivex.rxjava3.core.Observable
import javafx.beans.property.SimpleStringProperty
import javafx.collections.ListChangeListener
import tornadofx.*

class TextbooksViewModel : BaseViewModel() {

    var lstItems = mutableListOf<MTextbook>().asObservable()
    private val textbookService: TextbookService by inject()
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

    fun newTextbook() = MTextbook().apply {
        langid = vmSettings.selectedLang.id
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
