package com.zwstudio.lolly.data.phrases

import com.zwstudio.lolly.data.BaseViewModel
import com.zwstudio.lolly.data.applyIO
import com.zwstudio.lolly.domain.wpp.MLangPhrase
import com.zwstudio.lolly.service.LangPhraseService
import io.reactivex.rxjava3.core.Observable
import javafx.beans.property.SimpleStringProperty
import javafx.collections.ObservableList
import tornadofx.asObservable

class PhrasesLangViewModel : BaseViewModel() {

    var lstPhrasesAll = mutableListOf<MLangPhrase>().asObservable()
    var lstPhrasesFiltered: ObservableList<MLangPhrase>? = null
    val lstPhrases get() = lstPhrasesFiltered ?: lstPhrasesAll
    val langPhraseService: LangPhraseService by inject()

    val scopeFilter = SimpleStringProperty(vmSettings.lstScopePhraseFilters[0])
    val textFilter = SimpleStringProperty()

    fun reload() {
        langPhraseService.getDataByLang(vmSettings.selectedLang.id, vmSettings.lstTextbooks)
            .map { lstPhrasesAll.clear(); lstPhrasesAll.addAll(it); Unit }
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
