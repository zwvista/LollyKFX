package com.zwstudio.lolly.data.patterns

import com.zwstudio.lolly.domain.wpp.MWebPage
import com.zwstudio.lolly.service.wpp.WebPageService
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
        webPageService.getDataBySearch(title.value, url.value).subscribe {
            lstWebPages.setAll(it)
            selectedWebPage.value = null
        }
}
