package com.zwstudio.lolly.data.words

import com.zwstudio.lolly.data.BaseViewModel
import com.zwstudio.lolly.data.NoteViewModel
import com.zwstudio.lolly.data.applyIO
import com.zwstudio.lolly.domain.wpp.MLangWord
import com.zwstudio.lolly.service.LangWordService
import io.reactivex.rxjava3.core.Observable
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.ObservableList
import tornadofx.asObservable

class WordsLangViewModel : BaseViewModel() {

    var lstWordsAll = mutableListOf<MLangWord>().asObservable()
    var lstWordsFiltered: ObservableList<MLangWord>? = null
    val lstWords get() = lstWordsFiltered ?: lstWordsAll
    val vmNote: NoteViewModel by inject()
    val langWordService: LangWordService by inject()

    val newWord = SimpleStringProperty()
    val scopeFilter = SimpleStringProperty(vmSettings.lstScopeWordFilters[0])
    val textFilter = SimpleStringProperty()
    val levelge0only = SimpleBooleanProperty()

    fun reload() {
        langWordService.getDataByLang(vmSettings.selectedLang.id, vmSettings.lstTextbooks)
            .map { lstWordsAll.clear(); lstWordsAll.addAll(it); Unit }
            .applyIO()
            .subscribe()
    }

    fun update(id: Int, langid: Int, word: String, level: Int, note: String?): Observable<Unit> =
        langWordService.update(id, langid, word, note)
            .applyIO()

    fun create(langid: Int, word: String, level: Int, note: String?): Observable<Int> =
        langWordService.create(langid, word, note)
            .applyIO()

    fun delete(item: MLangWord): Observable<Unit> =
        langWordService.delete(item)
            .applyIO()

    fun newLangWord() = MLangWord().apply {
        langid = vmSettings.selectedLang.id
    }

    fun getNote(index: Int): Observable<Unit> {
        val item = lstWordsAll[index]
        return vmNote.getNote(item.word).concatMap {
            item.note = it
            langWordService.updateNote(item.id, it)
        }
    }
}
