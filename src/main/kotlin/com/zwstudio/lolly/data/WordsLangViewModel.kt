package com.zwstudio.lolly.data

import com.zwstudio.lolly.domain.MLangWord
import com.zwstudio.lolly.service.LangWordService
import com.zwstudio.lolly.service.WordFamiService
import io.reactivex.rxjava3.core.Observable

class WordsLangViewModel : BaseViewModel() {

    var lstWords = mutableListOf<MLangWord>()
    var isSwipeStarted = false

    val vmNote: NoteViewModel by inject()

    val langWordService: LangWordService by inject()
    val wordFamiService: WordFamiService by inject()

    fun getData(): Observable<Unit> =
        langWordService.getDataByLang(vmSettings.selectedLang.id, vmSettings.lstTextbooks)
            .map { lstWords = it.toMutableList() }
            .applyIO()

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
        val item = lstWords[index]
        return vmNote.getNote(item.word).concatMap {
            item.note = it
            langWordService.updateNote(item.id, it)
        }
    }
}
