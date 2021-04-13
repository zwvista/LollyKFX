package com.zwstudio.lolly.viewmodels.phrases

import com.zwstudio.lolly.viewmodels.misc.applyIO
import com.zwstudio.lolly.models.wpp.MLangPhrase
import com.zwstudio.lolly.services.wpp.LangPhraseService
import com.zwstudio.lolly.services.wpp.WordPhraseService
import io.reactivex.rxjava3.core.Observable
import tornadofx.*

open class PhrasesLangViewModel : PhrasesBaseViewModel() {

    var lstPhrasesAll = mutableListOf<MLangPhrase>()
    val lstPhrases = mutableListOf<MLangPhrase>().asObservable()
    val langPhraseService: LangPhraseService by inject()
    val wordPhraseService: WordPhraseService by inject()

    override fun applyFilters() {
        lstPhrases.setAll(if (textFilter.value.isEmpty()) lstPhrasesAll else lstPhrasesAll.filter {
            textFilter.value.isEmpty() || (if (scopeFilter.value == "Phrase") it.phrase else it.translation).contains(textFilter.value, true)
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

    fun getPhrases(wordid: Int?): Observable<Unit> =
        if (wordid == null)
            Observable.just(Unit)
                .doAfterNext { lstPhrases.clear() }
        else
            wordPhraseService.getPhrasesByWordId(wordid)
                .applyIO()
                .map { lstPhrases.setAll(it); Unit }

    fun unlink(wordid: Int, phraseid: Int): Observable<Unit> =
        wordPhraseService.getDataByWordPhrase(wordid, phraseid).flatMap {
            var o = Observable.just(Unit)
            for (item in it)
                o = o.concatWith(wordPhraseService.delete(item.id))
            return@flatMap o
        }.applyIO()
}
