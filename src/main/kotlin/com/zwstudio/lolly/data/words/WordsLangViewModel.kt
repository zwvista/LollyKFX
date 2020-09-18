package com.zwstudio.lolly.data.words

import com.zwstudio.lolly.data.misc.BaseViewModel
import com.zwstudio.lolly.data.misc.NoteViewModel
import com.zwstudio.lolly.data.misc.SettingsViewModel
import com.zwstudio.lolly.data.misc.applyIO
import com.zwstudio.lolly.domain.wpp.MLangPhrase
import com.zwstudio.lolly.domain.wpp.MLangWord
import com.zwstudio.lolly.service.wpp.LangWordService
import com.zwstudio.lolly.service.wpp.WordPhraseService
import io.reactivex.rxjava3.core.Observable
import javafx.beans.property.SimpleStringProperty
import tornadofx.asObservable

class WordsLangViewModel : BaseViewModel() {

    var lstWordsAll = mutableListOf<MLangWord>()
    val lstWords = mutableListOf<MLangWord>().asObservable()
    val vmNote: NoteViewModel by inject()
    val langWordService: LangWordService by inject()
    val lstPhrases = mutableListOf<MLangPhrase>().asObservable()
    val wordPhraseService: WordPhraseService by inject()

    val newWord = SimpleStringProperty()
    val scopeFilter = SimpleStringProperty(SettingsViewModel.lstScopeWordFilters[0])
    val textFilter = SimpleStringProperty("")
    val statusText = SimpleStringProperty()

    init {
        scopeFilter.addListener { _, _, _ -> applyFilters() }
        textFilter.addListener { _, _, _ -> applyFilters() }
    }

    private fun applyFilters() {
        lstWords.setAll(if (textFilter.value.isEmpty()) lstWordsAll else lstWordsAll.filter {
            textFilter.value.isEmpty() || (if (scopeFilter.value == "Word") it.word else it.note ?: "").contains(textFilter.value, true)
        })
        statusText.value = "${lstWords.size} Words in ${vmSettings.langInfo}"
    }

    fun reload() {
        langWordService.getDataByLang(vmSettings.selectedLang.id)
            .applyIO()
            .subscribe { lstWordsAll = it.toMutableList(); applyFilters() }
    }

    fun update(o: MLangWord): Observable<Unit> =
        langWordService.update(o.id, o.langid, o.word, o.note)
            .applyIO()

    fun create(o: MLangWord): Observable<Int> =
        langWordService.create(o.langid, o.word, o.note)
            .applyIO()

    fun delete(item: MLangWord): Observable<Unit> =
        langWordService.delete(item)
            .applyIO()

    fun newLangWord() = MLangWord().apply {
        langid = vmSettings.selectedLang.id
    }

    fun getNote(index: Int): Observable<Unit> {
        val item = lstWordsAll[index]
        return vmNote.getNote(item.word).flatMap {
            item.note = it
            langWordService.updateNote(item.id, it)
        }
    }

    fun searchPhrases(wordid: Int?) {
        if (wordid == null)
            lstPhrases.clear()
        else
            wordPhraseService.getPhrasesByWordId(wordid)
                .map { lstPhrases.setAll(it) }
                .applyIO()
                .subscribe()
    }
}
