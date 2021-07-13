package com.zwstudio.lolly.viewmodels.textbooks

import com.zwstudio.lolly.models.misc.MWebTextbook
import com.zwstudio.lolly.services.misc.WebTextbookService
import com.zwstudio.lolly.viewmodels.misc.BaseViewModel
import com.zwstudio.lolly.viewmodels.misc.applyIO
import io.reactivex.rxjava3.kotlin.subscribeBy
import javafx.beans.property.SimpleStringProperty
import javafx.collections.ListChangeListener
import tornadofx.*

class WebTextbooksViewModel : BaseViewModel() {

    var lstItems = mutableListOf<MWebTextbook>().asObservable()
    private val webTextbookService: WebTextbookService by inject()
    val statusText = SimpleStringProperty()

    init {
        lstItems.addListener(ListChangeListener {
            statusText.value = "${lstItems.size} WebTextbooks in ${vmSettings.langInfo}"
        })
    }

    fun reload() {
        webTextbookService.getDataByLang(vmSettings.selectedLang.id)
            .applyIO()
            .subscribeBy { lstItems.setAll(it) }
    }
}
