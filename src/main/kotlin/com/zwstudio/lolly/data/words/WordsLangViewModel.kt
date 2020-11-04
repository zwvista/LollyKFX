package com.zwstudio.lolly.data.words

import com.zwstudio.lolly.data.misc.applyIO
import com.zwstudio.lolly.domain.wpp.MLangWord
import com.zwstudio.lolly.service.wpp.LangWordService
import com.zwstudio.lolly.service.wpp.WordPhraseService
import io.reactivex.rxjava3.core.Observable
import tornadofx.*

class WordsLangViewModel : WordsBaseViewModel() {

    var lstWordsAll = mutableListOf<MLangWord>()
    val lstWords = mutableListOf<MLangWord>().asObservable()
    val langWordService: LangWordService by inject()
    val wordPhraseService: WordPhraseService by inject()

    override fun applyFilters() {
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

    fun retrieveNote(index: Int): Observable<Unit> {
        val item = lstWordsAll[index]
        return vmSettings.retrieveNote(item.word).flatMap {
            item.note = it
            langWordService.updateNote(item.id, it)
        }
    }

    fun getWords(phraseid: Int?): Observable<Unit> =
        if (phraseid == null)
            Observable.just(Unit)
                .doAfterNext { lstWords.clear() }
        else
            wordPhraseService.getWordsByPhraseId(phraseid)
                .applyIO()
                .map { lstWords.setAll(it); Unit }

    fun unlink(wordid: Int, phraseid: Int): Observable<Unit> =
        wordPhraseService.getDataByWordPhrase(wordid, phraseid).flatMap {
            var o = Observable.just(Unit)
            for (item in it)
                o = o.concatWith(wordPhraseService.delete(item.id))
            return@flatMap o
        }.applyIO()
}
