package com.zwstudio.lolly.viewmodels.words

import com.zwstudio.lolly.viewmodels.misc.BaseViewModel
import com.zwstudio.lolly.models.wpp.MUnitWord
import javafx.beans.property.SimpleStringProperty
import tornadofx.*

class WordsSearchViewModel : BaseViewModel() {
    var lstWords = mutableListOf<MUnitWord>().asObservable()
    var selectedWordIndex = 0
    val selectedWord: MUnitWord
        get() = lstWords[selectedWordIndex]

    val newWord = SimpleStringProperty()

    fun reload() {
        lstWords.clear()
    }
}
