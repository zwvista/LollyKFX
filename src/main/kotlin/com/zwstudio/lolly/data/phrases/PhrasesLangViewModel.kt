package com.zwstudio.lolly.data.phrases

import com.zwstudio.lolly.data.BaseViewModel
import com.zwstudio.lolly.data.applyIO
import com.zwstudio.lolly.domain.MLangPhrase
import com.zwstudio.lolly.service.LangPhraseService
import io.reactivex.rxjava3.core.Observable
import tornadofx.asObservable

class PhrasesLangViewModel : BaseViewModel() {

    var lstPhrases = mutableListOf<MLangPhrase>().asObservable()
    val langPhraseService: LangPhraseService by inject()

    fun reload() {
        getData().subscribe()
    }

    private fun getData(): Observable<Unit> =
        langPhraseService.getDataByLang(vmSettings.selectedLang.id, vmSettings.lstTextbooks)
            .map { lstPhrases.clear(); lstPhrases.addAll(it); Unit }
            .applyIO()

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
