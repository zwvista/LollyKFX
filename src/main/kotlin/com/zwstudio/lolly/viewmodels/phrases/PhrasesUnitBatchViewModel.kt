package com.zwstudio.lolly.viewmodels.phrases

import com.zwstudio.lolly.models.misc.MSelectItem
import com.zwstudio.lolly.models.wpp.MUnitPhrase
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import tornadofx.*

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
        for (o in vm.lstPhrasesAll) {
            if (!o.isChecked.value) continue
            var b = false
            if (unitIsChecked.value) {
                o.unit = unititem.value.value
                b = true
            }
            if (partIsChecked.value) {
                o.part = partitem.value.value
                b = true
            }
            if (seqNumIsChecked.value) {
                o.seqnum += seqnum.value
                b = true
            }
            if (b)
                vm.update(o).subscribe()
        }
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
