package com.zwstudio.lolly.data.textbooks

import com.zwstudio.lolly.data.misc.BaseViewModel
import com.zwstudio.lolly.data.misc.applyIO
import com.zwstudio.lolly.domain.misc.MTextbook
import com.zwstudio.lolly.domain.misc.MWebTextbook
import com.zwstudio.lolly.service.misc.TextbookService
import com.zwstudio.lolly.service.misc.WebTextbookService
import io.reactivex.rxjava3.core.Observable
import javafx.beans.property.SimpleStringProperty
import javafx.collections.ListChangeListener
import tornadofx.*

class WebTextbooksViewModel : BaseViewModel() {

    var lstItems = mutableListOf<MWebTextbook>().asObservable()
    val webTextbookService: WebTextbookService by inject()
    val statusText = SimpleStringProperty()

    init {
        lstItems.addListener(ListChangeListener {
            statusText.value = "${lstItems.size} WebTextbooks in ${vmSettings.langInfo}"
        })
    }

    fun reload() {
        webTextbookService.getDataByLang(vmSettings.selectedLang.id)
            .applyIO()
            .subscribe { lstItems.setAll(it) }
    }
}
