package com.zwstudio.lolly.data

import com.zwstudio.lolly.domain.MLangPhrase
import com.zwstudio.lolly.service.LangPhraseService
import io.reactivex.rxjava3.core.Observable

class PhrasesLangViewModel : BaseViewModel() {

    var lstPhrases = mutableListOf<MLangPhrase>()
    var isSwipeStarted = false

    val langPhraseService: LangPhraseService by inject()

    fun getData(): Observable<Unit> =
        langPhraseService.getDataByLang(vmSettings.selectedLang.id, vmSettings.lstTextbooks)
            .map { lstPhrases = it.toMutableList() }
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
