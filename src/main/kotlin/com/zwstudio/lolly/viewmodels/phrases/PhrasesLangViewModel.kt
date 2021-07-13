package com.zwstudio.lolly.viewmodels.phrases

import com.zwstudio.lolly.models.wpp.MLangPhrase
import com.zwstudio.lolly.services.wpp.LangPhraseService
import com.zwstudio.lolly.services.wpp.WordPhraseService
import com.zwstudio.lolly.viewmodels.misc.applyIO
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.kotlin.subscribeBy
import tornadofx.*

open class PhrasesLangViewModel : PhrasesBaseViewModel() {

    var lstPhrasesAll = mutableListOf<MLangPhrase>()
    val lstPhrases = mutableListOf<MLangPhrase>().asObservable()
    private val langPhraseService: LangPhraseService by inject()
    protected val wordPhraseService: WordPhraseService by inject()

    override fun applyFilters() {
        lstPhrases.setAll(if (textFilter.value.isEmpty()) lstPhrasesAll else lstPhrasesAll.filter {
            textFilter.value.isEmpty() || (if (scopeFilter.value == "Phrase") it.phrase else it.translation).contains(textFilter.value, true)
        })
        statusText.value = "${lstPhrases.size} Phrases in ${vmSettings.langInfo}"
    }

    fun reload() {
        langPhraseService.getDataByLang(vmSettings.selectedLang.id)
            .applyIO()
            .subscribeBy { lstPhrasesAll = it.toMutableList(); applyFilters() }
    }

    fun update(o: MLangPhrase): Completable =
        langPhraseService.update(o.id, o.langid, o.phrase, o.translation)
            .applyIO()

    fun create(o: MLangPhrase): Single<Int> =
        langPhraseService.create(o.langid, o.phrase, o.translation)
            .applyIO()

    fun delete(item: MLangPhrase): Completable =
        langPhraseService.delete(item)
            .applyIO()

    fun newLangPhrase() = MLangPhrase().apply {
        langid = vmSettings.selectedLang.id
    }

    fun getPhrases(wordid: Int?): Completable =
        if (wordid == null)
            Completable.complete()
                .doOnComplete { lstPhrases.clear() }
        else
            wordPhraseService.getPhrasesByWordId(wordid)
                .applyIO()
                .flatMapCompletable { lstPhrases.setAll(it); Completable.complete() }

    fun unlink(wordid: Int, phraseid: Int): Completable =
        wordPhraseService.getDataByWordPhrase(wordid, phraseid).flatMapCompletable {
            var o = Completable.complete()
            for (item in it)
                o = o.mergeWith(wordPhraseService.delete(item.id))
            o
        }.applyIO()
}
