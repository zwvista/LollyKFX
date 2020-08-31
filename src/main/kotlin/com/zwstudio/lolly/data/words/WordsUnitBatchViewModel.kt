package com.zwstudio.lolly.data.words

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleIntegerProperty
import tornadofx.ViewModel

class WordsUnitBatchViewModel(val vm: WordsUnitViewModel) : ViewModel() {
    val unitIsChecked = SimpleBooleanProperty()
    val partIsChecked = SimpleBooleanProperty()
    val seqNumIsChecked = SimpleBooleanProperty()
    val levelIsChecked = SimpleBooleanProperty()
    val level0OnlyIsChecked = SimpleBooleanProperty()
    val unit = SimpleIntegerProperty()
    val part = SimpleIntegerProperty()
    val seqnum = SimpleIntegerProperty()
    val level = SimpleIntegerProperty()

    init {
    }

    override fun onCommit() {
        super.onCommit()
    }
}
