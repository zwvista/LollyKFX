package com.zwstudio.lolly.data.phrases

import com.zwstudio.lolly.data.misc.BaseViewModel
import com.zwstudio.lolly.data.misc.SettingsViewModel
import com.zwstudio.lolly.data.misc.applyIO
import com.zwstudio.lolly.domain.wpp.MLangPhrase
import com.zwstudio.lolly.service.wpp.LangPhraseService
import io.reactivex.rxjava3.core.Observable
import javafx.beans.property.SimpleStringProperty
import tornadofx.asObservable

class PhrasesLangViewModel : BaseViewModel() {

    var lstPhrasesAll = mutableListOf<MLangPhrase>()
    val lstPhrases = mutableListOf<MLangPhrase>().asObservable()
    val langPhraseService: LangPhraseService by inject()

    val scopeFilter = SimpleStringProperty(SettingsViewModel.lstScopePhraseFilters[0])
    val textFilter = SimpleStringProperty("")
    val statusText = SimpleStringProperty()

    init {
        scopeFilter.addListener { _, _, _ -> applyFilters() }
        textFilter.addListener { _, _, _ -> applyFilters() }
    }

    private fun applyFilters() {
        lstPhrases.setAll(if (textFilter.value.isEmpty()) lstPhrasesAll else lstPhrasesAll.filter {
            textFilter.value.isEmpty() || (if (scopeFilter.value == "Phrase") it.phrase else it.translation ?: "").contains(textFilter.value, true)
        })
        statusText.value = "${lstPhrases.size} Phrases in ${vmSettings.langInfo}"
    }

    fun reload() {
        langPhraseService.getDataByLang(vmSettings.selectedLang.id)
            .applyIO()
            .subscribe { lstPhrasesAll = it.toMutableList(); applyFilters() }
    }

    fun update(o: MLangPhrase): Observable<Unit> =
        langPhraseService.update(o.id, o.langid, o.phrase, o.translation)
            .applyIO()

    fun create(o: MLangPhrase): Observable<Int> =
        langPhraseService.create(o.langid, o.phrase, o.translation)
            .applyIO()

    fun delete(item: MLangPhrase): Observable<Unit> =
        langPhraseService.delete(item)
            .applyIO()

    fun newLangPhrase() = MLangPhrase().apply {
        langid = vmSettings.selectedLang.id
    }
}
