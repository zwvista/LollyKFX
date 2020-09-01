package com.zwstudio.lolly.data.words

import com.zwstudio.lolly.domain.MSelectItem
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
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
    val unititem = SimpleObjectProperty<MSelectItem>()
    val partitem = SimpleObjectProperty<MSelectItem>()
    val selectedTextbook get() = vm.vmSettings.selectedTextbook

    override fun onCommit() {
        super.onCommit()
    }
}
