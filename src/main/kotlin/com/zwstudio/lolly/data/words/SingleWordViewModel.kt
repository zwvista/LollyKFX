package com.zwstudio.lolly.data.words

import com.zwstudio.lolly.data.misc.BaseViewModel
import com.zwstudio.lolly.data.misc.applyIO
import com.zwstudio.lolly.domain.wpp.MUnitWord
import com.zwstudio.lolly.service.wpp.UnitWordService
import tornadofx.asObservable

class SingleWordViewModel(word: String) : BaseViewModel() {
    var lstWords = mutableListOf<MUnitWord>().asObservable()
    val unitWordService: UnitWordService by inject()

    init {
        unitWordService.getDataByLangWord(vmSettings.selectedLang.id, word, vmSettings.lstTextbooks)
            .map { lstWords.setAll(it); Unit }
            .applyIO()
            .subscribe()
    }
}
