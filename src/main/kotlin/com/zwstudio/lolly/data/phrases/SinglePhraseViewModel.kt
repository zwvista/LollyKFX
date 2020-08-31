package com.zwstudio.lolly.data.phrases

import com.zwstudio.lolly.data.BaseViewModel
import com.zwstudio.lolly.data.applyIO
import com.zwstudio.lolly.domain.wpp.MUnitPhrase
import com.zwstudio.lolly.service.UnitPhraseService
import tornadofx.asObservable

class SinglePhraseViewModel(phrase: String) : BaseViewModel() {
    var lstPhrases = mutableListOf<MUnitPhrase>().asObservable()
    val unitPhraseService: UnitPhraseService by inject()

    init {
        unitPhraseService.getDataByLangPhrase(vmSettings.selectedLang.id, phrase, vmSettings.lstTextbooks)
            .map { lstPhrases.clear(); lstPhrases.addAll(it); Unit }
            .applyIO()
            .subscribe()
    }
}
