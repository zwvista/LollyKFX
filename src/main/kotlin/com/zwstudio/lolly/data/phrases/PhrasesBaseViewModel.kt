package com.zwstudio.lolly.data.phrases

import com.zwstudio.lolly.data.misc.BaseViewModel
import com.zwstudio.lolly.data.misc.SettingsViewModel
import com.zwstudio.lolly.domain.misc.MSelectItem
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty

open class PhrasesBaseViewModel : BaseViewModel() {

    val scopeFilter = SimpleStringProperty(SettingsViewModel.lstScopePhraseFilters[0])
    val textFilter = SimpleStringProperty("")
    val textbookFilter = SimpleObjectProperty<MSelectItem>()
    val statusText = SimpleStringProperty()

    init {
        scopeFilter.addListener { _, _, _ -> applyFilters() }
        textFilter.addListener { _, _, _ -> applyFilters() }
        textbookFilter.addListener { _, _, _ -> applyFilters() }
    }

    open fun applyFilters() {
    }
}
