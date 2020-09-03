package com.zwstudio.lolly.data.phrases

import com.zwstudio.lolly.data.BaseViewModel
import com.zwstudio.lolly.data.applyIO
import com.zwstudio.lolly.domain.wpp.MLangPhrase
import com.zwstudio.lolly.service.LangPhraseService
import io.reactivex.rxjava3.core.Observable
import javafx.beans.property.SimpleStringProperty
import tornadofx.asObservable

class PhrasesLangViewModel : BaseViewModel() {

    var lstPhrasesAll = mutableListOf<MLangPhrase>().asObservable()
    val lstPhrases = mutableListOf<MLangPhrase>().asObservable()
    val langPhraseService: LangPhraseService by inject()

    val scopeFilter = SimpleStringProperty(vmSettings.lstScopePhraseFilters[0])
    val textFilter = SimpleStringProperty()

    init {
        scopeFilter.addListener { _, _, _ -> applyFilters() }
        textFilter.addListener { _, _, _ -> applyFilters() }
    }

    private fun applyFilters() =
        lstPhrases.setAll(lstPhrasesAll.filtered {
            (textFilter.value.isNullOrEmpty() || (if (scopeFilter.value == "Phrase") it.phrase else it.translation ?: "").contains(textFilter.value, true))
        })

    fun reload() {
        langPhraseService.getDataByLang(vmSettings.selectedLang.id, vmSettings.lstTextbooks)
            .map { lstPhrasesAll.setAll(it); applyFilters() }
            .applyIO()
            .subscribe()
    }

    fun update(id: Int, langid: Int, phrase: String, translation: String?): Observable<Unit> =
        langPhraseService.update(id, langid, phrase, translation)
            .applyIO()

    fun create(langid: Int, phrase: String, translation: String?): Observable<Int> =
        langPhraseService.create(langid, phrase, translation)
            .applyIO()

    fun delete(item: MLangPhrase): Observable<Unit> =
        langPhraseService.delete(item)
            .applyIO()

    fun newLangPhrase() = MLangPhrase().apply {
        langid = vmSettings.selectedLang.id
    }
}
