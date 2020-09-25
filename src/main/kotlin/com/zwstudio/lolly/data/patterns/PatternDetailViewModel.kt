package com.zwstudio.lolly.data.patterns

import com.zwstudio.lolly.domain.wpp.MPattern
import tornadofx.*

class PatternDetailViewModel(val vm: PatternsViewModel, item: MPattern) : ItemViewModel<MPattern>(item) {
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
