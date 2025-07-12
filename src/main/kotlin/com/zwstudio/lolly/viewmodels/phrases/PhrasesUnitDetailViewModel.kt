package com.zwstudio.lolly.viewmodels.phrases

import com.zwstudio.lolly.models.misc.MSelectItem
import com.zwstudio.lolly.models.wpp.MUnitPhrase
import javafx.beans.property.Property
import tornadofx.ItemViewModel

class PhrasesUnitDetailViewModel(val vm: PhrasesUnitViewModel, item: MUnitPhrase) : ItemViewModel<MUnitPhrase>(item) {
    val id = bind(MUnitPhrase::id)
    val textbookname = bind(MUnitPhrase::textbookname)
    val unititem: Property<MSelectItem>
    val partitem: Property<MSelectItem>
    val seqnum = bind(MUnitPhrase::seqnum)
    val phraseid = bind(MUnitPhrase::phraseid)
    val phrase = bind(MUnitPhrase::phraseProperty)
    val translation = bind(MUnitPhrase::translationProperty)
    val vmSingle = SinglePhraseViewModel(item.phrase)

    init {
        item.unititem = item.textbook.lstUnits.first { it.value == item.unit }
        item.partitem = item.textbook.lstParts.first { it.value == item.part }
        unititem = bind(MUnitPhrase::unititem)
        partitem = bind(MUnitPhrase::partitem)
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
