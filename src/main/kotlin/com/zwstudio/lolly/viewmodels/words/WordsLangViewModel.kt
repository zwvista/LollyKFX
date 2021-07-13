package com.zwstudio.lolly.viewmodels.words

import com.zwstudio.lolly.models.wpp.MLangWord
import com.zwstudio.lolly.services.wpp.LangWordService
import com.zwstudio.lolly.services.wpp.WordPhraseService
import com.zwstudio.lolly.viewmodels.misc.SettingsViewModel
import com.zwstudio.lolly.viewmodels.misc.applyIO
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.kotlin.subscribeBy
import tornadofx.*

open class WordsLangViewModel : WordsBaseViewModel() {

    var lstWordsAll = mutableListOf<MLangWord>()
    val lstWords = mutableListOf<MLangWord>().asObservable()
    private val langWordService: LangWordService by inject()
    protected val wordPhraseService: WordPhraseService by inject()

    override fun applyFilters() {
        lstWords.setAll(if (textFilter.value.isEmpty()) lstWordsAll else lstWordsAll.filter {
            textFilter.value.isEmpty() || (if (scopeFilter.value == "Word") it.word else it.note).contains(textFilter.value, true)
        })
        statusText.value = "${lstWords.size} Words in ${vmSettings.langInfo}"
    }

    fun reload() {
        langWordService.getDataByLang(vmSettings.selectedLang.id)
            .applyIO()
            .subscribeBy { lstWordsAll = it.toMutableList(); applyFilters() }
    }

    fun update(o: MLangWord): Completable =
        langWordService.update(o.id, o.langid, o.word, o.note)
            .applyIO()

    fun create(o: MLangWord): Single<Int> =
        langWordService.create(o.langid, o.word, o.note)
            .applyIO()

    fun delete(item: MLangWord): Completable =
        langWordService.delete(item)
            .applyIO()

    fun newLangWord() = MLangWord().apply {
        langid = vmSettings.selectedLang.id
    }

    fun addNewWord(): Completable {
        val item = newLangWord().apply { word = newWord.value }
        newWord.value = ""
        return create(item).flatMapCompletable { Completable.complete() }
    }

    fun retrieveNote(item: MLangWord): Completable {
        return vmSettings.retrieveNote(item.word).flatMapCompletable {
            item.note = it
            langWordService.updateNote(item.id, item.note)
        }
    }

    fun clearNote(item: MLangWord): Completable {
        item.note = SettingsViewModel.zeroNote
        return langWordService.updateNote(item.id, item.note)
    }

    fun getWords(phraseid: Int?): Completable =
        if (phraseid == null)
            Completable.complete()
                .doOnComplete { lstWords.clear() }
        else
            wordPhraseService.getWordsByPhraseId(phraseid)
                .applyIO()
                .flatMapCompletable { lstWords.setAll(it); Completable.complete() }

    fun unlink(wordid: Int, phraseid: Int): Completable =
        wordPhraseService.getDataByWordPhrase(wordid, phraseid).flatMapCompletable {
            var o = Completable.complete()
            for (item in it)
                o = o.mergeWith(wordPhraseService.delete(item.id))
            o
        }.applyIO()
}
