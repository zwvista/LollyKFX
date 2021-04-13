package com.zwstudio.lolly.viewmodels.phrases

import com.zwstudio.lolly.viewmodels.misc.BaseViewModel
import com.zwstudio.lolly.viewmodels.misc.applyIO
import com.zwstudio.lolly.models.wpp.MUnitPhrase
import com.zwstudio.lolly.services.wpp.UnitPhraseService
import tornadofx.*

class SinglePhraseViewModel(phrase: String) : BaseViewModel() {
    var lstPhrases = mutableListOf<MUnitPhrase>().asObservable()
    val unitPhraseService: UnitPhraseService by inject()

    init {
        unitPhraseService.getDataByLangPhrase(vmSettings.selectedLang.id, phrase, vmSettings.lstTextbooks)
            .map { lstPhrases.setAll(it); Unit }
            .applyIO()
            .subscribe()
    }
}
