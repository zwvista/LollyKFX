package com.zwstudio.lolly.data.phrases

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleIntegerProperty
import tornadofx.ViewModel

class PhrasesUnitBatchViewModel(val vm: PhrasesUnitViewModel) : ViewModel() {
    val unitIsChecked = SimpleBooleanProperty()
    val partIsChecked = SimpleBooleanProperty()
    val seqNumIsChecked = SimpleBooleanProperty()
    val unit = SimpleIntegerProperty()
    val part = SimpleIntegerProperty()
    val seqnum = SimpleIntegerProperty()

    init {
    }

    override fun onCommit() {
        super.onCommit()
    }
}
