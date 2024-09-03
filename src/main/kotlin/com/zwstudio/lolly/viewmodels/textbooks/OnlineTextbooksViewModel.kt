package com.zwstudio.lolly.viewmodels.onlinetextbooks

import com.zwstudio.lolly.models.misc.MOnlineTextbook
import com.zwstudio.lolly.services.misc.OnlineTextbookService
import com.zwstudio.lolly.viewmodels.misc.BaseViewModel
import com.zwstudio.lolly.viewmodels.misc.applyIO
import io.reactivex.rxjava3.kotlin.subscribeBy
import javafx.beans.property.SimpleStringProperty
import javafx.collections.ListChangeListener
import tornadofx.asObservable

class OnlineTextbooksViewModel : BaseViewModel() {

    var lstItems = mutableListOf<MOnlineTextbook>().asObservable()
    private val onlineTextbookService: OnlineTextbookService by inject()
    val statusText = SimpleStringProperty()

    init {
        lstItems.addListener(ListChangeListener {
            statusText.value = "${lstItems.size} Online Textbooks in ${vmSettings.langInfo}"
        })
    }

    fun reload() {
        onlineTextbookService.getDataByLang(vmSettings.selectedLang.id)
            .applyIO()
            .subscribeBy { lstItems.setAll(it) }
    }
}
