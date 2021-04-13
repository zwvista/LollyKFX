package com.zwstudio.lolly.viewmodels.patterns

import com.zwstudio.lolly.models.wpp.MPattern
import tornadofx.*

class PatternsDetailViewModel(val vm: PatternsViewModel, item: MPattern) : ItemViewModel<MPattern>(item) {
    val id = bind(MPattern::id)
    val pattern = bind(MPattern::pattern)
    val note = bind(MPattern::noteProperty)
    val tags = bind(MPattern::tagsProperty)

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
