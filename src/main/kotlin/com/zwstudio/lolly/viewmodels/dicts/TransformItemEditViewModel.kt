package com.zwstudio.lolly.viewmodels.dicts

import com.zwstudio.lolly.models.misc.MTransformItem
import tornadofx.*

class TransformItemEditViewModel(item: MTransformItem) : ItemViewModel<MTransformItem>(item) {
    val index = bind(MTransformItem::index)
    val extractor = bind(MTransformItem::extractor)
    val replacement = bind(MTransformItem::replacement)

    init {
    }

}
