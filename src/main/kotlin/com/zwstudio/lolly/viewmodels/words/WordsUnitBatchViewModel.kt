package com.zwstudio.lolly.viewmodels.words

import com.zwstudio.lolly.models.misc.MSelectItem
import com.zwstudio.lolly.models.wpp.MUnitWord
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import tornadofx.ViewModel

class WordsUnitBatchViewModel(val vm: WordsUnitViewModel) : ViewModel() {
    val unitChecked = SimpleBooleanProperty()
    val partChecked = SimpleBooleanProperty()
    val seqNumChecked = SimpleBooleanProperty()
    val unititem = SimpleObjectProperty<MSelectItem>()
    val partitem = SimpleObjectProperty<MSelectItem>()
    val seqnum = SimpleIntegerProperty()
    val selectedTextbook get() = vm.vmSettings.selectedTextbook

    init {
        for (o in vm.lstWordsAll)
            o.isChecked.value = false
    }

    override fun onCommit() {
        super.onCommit()
        for (o in vm.lstWordsAll) {
            if (!o.isChecked.value) continue
            var b = false
            if (unitChecked.value) {
                o.unit = unititem.value.value
                b = true
            }
            if (partChecked.value) {
                o.part = partitem.value.value
                b = true
            }
            if (seqNumChecked.value) {
                o.seqnum += seqnum.value
                b = true
            }
            if (b)
                vm.update(o).subscribe()
        }
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
