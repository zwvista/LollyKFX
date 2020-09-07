package com.zwstudio.lolly.data.words

import com.zwstudio.lolly.domain.MSelectItem
import com.zwstudio.lolly.domain.wpp.MUnitWord
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
    val unititem = SimpleObjectProperty<MSelectItem>()
    val partitem = SimpleObjectProperty<MSelectItem>()
    val seqnum = SimpleIntegerProperty()
    val level = SimpleIntegerProperty()
    val selectedTextbook get() = vm.vmSettings.selectedTextbook

    init {
        for (o in vm.lstWordsAll)
            o.isChecked.value = false
    }

    override fun onCommit() {
        super.onCommit()
    }

    fun checkItems(n: Int, selectedItems: List<MUnitWord>) {
        for (o in vm.lstWordsAll)
            o.isChecked.value = when (n) {
                0 -> true
                1 -> false
                else -> if (!selectedItems.contains(o)) o.isChecked.value else n == 2
            }
    }
}
