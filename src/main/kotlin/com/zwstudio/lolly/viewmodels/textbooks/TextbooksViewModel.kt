package com.zwstudio.lolly.viewmodels.textbooks

import com.zwstudio.lolly.models.misc.MTextbook
import com.zwstudio.lolly.services.misc.TextbookService
import com.zwstudio.lolly.viewmodels.misc.BaseViewModel
import com.zwstudio.lolly.viewmodels.misc.applyIO
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.kotlin.subscribeBy
import javafx.beans.property.SimpleStringProperty
import javafx.collections.ListChangeListener
import tornadofx.asObservable

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
            .subscribeBy { lstItems.setAll(it) }
    }

    fun newTextbook() = MTextbook().apply {
        langid = vmSettings.selectedLang.id
    }

    fun update(o: MTextbook): Completable =
        textbookService.update(o)
        .applyIO()

    fun create(o: MTextbook): Single<Int> =
        textbookService.create(o)
        .applyIO()

    fun delete(id: Int): Completable =
        textbookService.delete(id)
        .applyIO()
}
