package com.zwstudio.lolly.viewmodels.words

import com.zwstudio.lolly.models.misc.MSelectItem
import com.zwstudio.lolly.models.wpp.MUnitWord
import javafx.beans.property.Property
import tornadofx.ItemViewModel

class WordsUnitDetailViewModel(val vm: WordsUnitViewModel, item: MUnitWord) : ItemViewModel<MUnitWord>(item) {
    val id = bind(MUnitWord::id)
    val textbookname = bind(MUnitWord::textbookname)
    val unititem: Property<MSelectItem>
    val partitem: Property<MSelectItem>
    val seqnum = bind(MUnitWord::seqnum)
    val wordid = bind(MUnitWord::wordid)
    val word = bind(MUnitWord::wordProperty)
    val note = bind(MUnitWord::noteProperty)
    val famiid = bind(MUnitWord::famiid)
    val accuracy = bind(MUnitWord::accuracy)
    val vmSingle = SingleWordViewModel(item.word)

    init {
        item.unititem = item.textbook.lstUnits.first { it.value == item.unit }
        item.partitem = item.textbook.lstParts.first { it.value == item.part }
        unititem = bind(MUnitWord::unititem)
        partitem = bind(MUnitWord::partitem)
    }

    override fun onCommit() {
        super.onCommit()
        item.unit = item.unititem!!.value
        item.part = item.partitem!!.value
        if (item.id == 0)
            vm.create(item).subscribe()
        else
            vm.update(item).subscribe()
    }
}
