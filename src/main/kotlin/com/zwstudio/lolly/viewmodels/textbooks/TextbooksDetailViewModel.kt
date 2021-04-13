package com.zwstudio.lolly.viewmodels.textbooks

import com.zwstudio.lolly.models.misc.MTextbook
import tornadofx.*

class TextbooksDetailViewModel(val vm: TextbooksViewModel, item: MTextbook) : ItemViewModel<MTextbook>(item) {
    val id = bind(MTextbook::id)
    val langname = vm.vmSettings.selectedLang.langname
    val textbookname = bind(MTextbook::textbookname)
    val units = bind(MTextbook::units)
    val parts = bind(MTextbook::parts)

    init {
    }

    override fun onCommit() {
        super.onCommit()
        if (item.id == 0)
            vm.create(item).subscribe()
        else
            vm.update(item).subscribe()
    }
}
