package com.zwstudio.lolly.viewmodels.patterns

import com.zwstudio.lolly.models.wpp.MPattern
import com.zwstudio.lolly.services.wpp.PatternService
import com.zwstudio.lolly.viewmodels.misc.BaseViewModel
import com.zwstudio.lolly.viewmodels.misc.SettingsViewModel
import com.zwstudio.lolly.viewmodels.misc.applyIO
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.kotlin.subscribeBy
import javafx.beans.property.SimpleStringProperty
import tornadofx.*

class PatternsViewModel : BaseViewModel() {

    var lstPatternsAll = mutableListOf<MPattern>()
    val lstPatterns = mutableListOf<MPattern>().asObservable()

    private val patternService: PatternService by inject()

    val scopeFilter = SimpleStringProperty(SettingsViewModel.lstScopePatternFilters[0])
    val textFilter = SimpleStringProperty("")
    val noFilter get() = textFilter.value.isEmpty()
    val statusText = SimpleStringProperty()

    init {
        scopeFilter.addListener { _, _, _ -> applyFilters() }
        textFilter.addListener { _, _, _ -> applyFilters() }
    }

    private fun applyFilters() {
        lstPatterns.setAll(if (noFilter) lstPatternsAll else lstPatternsAll.filter {
            (textFilter.value.isEmpty() || (if (scopeFilter.value == "Pattern") it.pattern else if (scopeFilter.value == "Note") it.note else it.tags).contains(textFilter.value, true))
        })
        statusText.value = "${lstPatterns.size} Patterns in ${vmSettings.langInfo}"
    }

    fun reload() {
        patternService.getDataByLang(vmSettings.selectedLang.id)
            .applyIO()
            .subscribeBy { lstPatternsAll = it.toMutableList(); applyFilters() }
    }

    fun update(item: MPattern): Completable =
        patternService.update(item)
            .applyIO()
    fun create(item: MPattern): Single<Int> =
        patternService.create(item)
            .applyIO()
    fun delete(id: Int): Completable =
        patternService.delete(id)
            .applyIO()
    fun newPattern() = MPattern().apply {
        langid = vmSettings.selectedLang.id
    }

}
