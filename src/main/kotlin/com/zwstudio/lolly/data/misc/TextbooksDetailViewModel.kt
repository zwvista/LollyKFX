package com.zwstudio.lolly.data.misc

import com.zwstudio.lolly.domain.misc.MTextbook
import tornadofx.*

class TextbooksDetailViewModel(val vm: TextbooksViewModel, item: MTextbook) : ItemViewModel<MTextbook>(item) {
    val id = bind(MTextbook::id)
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
