package com.zwstudio.lolly.viewmodels.patterns

import com.zwstudio.lolly.models.wpp.MWebPage
import com.zwstudio.lolly.services.wpp.WebPageService
import io.reactivex.rxjava3.kotlin.subscribeBy
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*

class WebPageSelectViewModel : ViewModel() {
    val title = SimpleStringProperty("")
    val url = SimpleStringProperty("")
    var lstWebPages = mutableListOf<MWebPage>().asObservable()
    var selectedWebPage = SimpleObjectProperty<MWebPage>()

    private val webPageService: WebPageService by inject()

    init {
    }

    fun search() =
        webPageService.getDataBySearch(title.value, url.value).subscribeBy {
            lstWebPages.setAll(it)
            selectedWebPage.value = null
        }
}
