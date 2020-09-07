package com.zwstudio.lolly.data.phrases

import com.zwstudio.lolly.domain.MSelectItem
import com.zwstudio.lolly.domain.wpp.MUnitPhrase
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import tornadofx.ViewModel

class PhrasesUnitBatchViewModel(val vm: PhrasesUnitViewModel) : ViewModel() {
    val unitIsChecked = SimpleBooleanProperty()
    val partIsChecked = SimpleBooleanProperty()
    val seqNumIsChecked = SimpleBooleanProperty()
    val unititem = SimpleObjectProperty<MSelectItem>()
    val partitem = SimpleObjectProperty<MSelectItem>()
    val seqnum = SimpleIntegerProperty()
    val selectedTextbook get() = vm.vmSettings.selectedTextbook

    init {
        for (o in vm.lstPhrasesAll)
            o.isChecked.value = false
    }

    override fun onCommit() {
        super.onCommit()
    }

    fun checkItems(n: Int, selectedItems: List<MUnitPhrase>) {
        for (o in vm.lstPhrasesAll)
            o.isChecked.value = when (n) {
                0 -> true
                1 -> false
                else -> if (!selectedItems.contains(o)) o.isChecked.value else n == 2
            }
    }
}
