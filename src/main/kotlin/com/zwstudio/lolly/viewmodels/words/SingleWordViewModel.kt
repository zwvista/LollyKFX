package com.zwstudio.lolly.viewmodels.words

import com.zwstudio.lolly.models.wpp.MUnitWord
import com.zwstudio.lolly.services.wpp.UnitWordService
import com.zwstudio.lolly.viewmodels.misc.BaseViewModel
import com.zwstudio.lolly.viewmodels.misc.applyIO
import tornadofx.asObservable

class SingleWordViewModel(word: String) : BaseViewModel() {
    var lstWords = mutableListOf<MUnitWord>().asObservable()
    private val unitWordService: UnitWordService by inject()

    init {
        unitWordService.getDataByLangWord(vmSettings.selectedLang.id, word, vmSettings.lstTextbooks)
            .map { lstWords.setAll(it); Unit }
            .applyIO()
            .subscribe()
    }
}
