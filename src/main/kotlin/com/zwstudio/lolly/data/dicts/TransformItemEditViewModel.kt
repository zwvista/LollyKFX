package com.zwstudio.lolly.data.dicts

import com.zwstudio.lolly.domain.misc.MTransformItem
import tornadofx.*

class TransformItemEditViewModel(item: MTransformItem) : ItemViewModel<MTransformItem>(item) {
    val index = bind(MTransformItem::index)
    val extractor = bind(MTransformItem::extractor)
    val replacement = bind(MTransformItem::replacement)

    init {
    }

    override fun onCommit() {
        super.onCommit()
    }
}
