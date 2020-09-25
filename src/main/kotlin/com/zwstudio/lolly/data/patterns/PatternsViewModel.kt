package com.zwstudio.lolly.data.patterns

import com.zwstudio.lolly.data.misc.BaseViewModel
import com.zwstudio.lolly.data.misc.SettingsViewModel
import com.zwstudio.lolly.data.misc.applyIO
import com.zwstudio.lolly.domain.wpp.MPattern
import com.zwstudio.lolly.domain.wpp.MPatternPhrase
import com.zwstudio.lolly.domain.wpp.MPatternWebPage
import com.zwstudio.lolly.service.wpp.PatternService
import io.reactivex.rxjava3.core.Observable
import javafx.beans.property.SimpleStringProperty
import tornadofx.*

class PatternsViewModel : BaseViewModel() {

    var lstPatternsAll = mutableListOf<MPattern>()
    val lstPatterns = mutableListOf<MPattern>().asObservable()
    var lstWebPages = mutableListOf<MPatternWebPage>()
    var lstPhrases = mutableListOf<MPatternPhrase>()

    val patternService: PatternService by inject()

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
            (textFilter.value.isEmpty() || (if (scopeFilter.value == "Pattern") it.pattern else if (scopeFilter.value == "Note") it.note ?: "" else it.tags ?: "").contains(textFilter.value, true))
        })
        statusText.value = "${lstPatterns.size} Patterns in ${vmSettings.langInfo}"
    }

    fun reload() {
        patternService.getDataByLang(vmSettings.selectedLang.id)
            .applyIO()
            .subscribe { lstPatternsAll = it.toMutableList(); applyFilters() }
    }

    fun update(item: MPattern): Observable<Unit> =
        patternService.update(item)
            .applyIO()

    fun create(item: MPattern): Observable<Int> =
        patternService.create(item)
            .applyIO()

    fun delete(id: Int): Observable<Unit> =
        patternService.delete(id)
            .applyIO()

    fun newPattern() = MPattern().apply {
        langid = vmSettings.selectedLang.id
    }
}
