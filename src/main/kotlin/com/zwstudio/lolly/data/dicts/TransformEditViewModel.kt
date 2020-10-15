package com.zwstudio.lolly.data.dicts

import com.zwstudio.lolly.data.misc.toTransformItems
import javafx.beans.property.SimpleIntegerProperty
import tornadofx.*

class TransformEditViewModel(val vmDetail: DictsDetailViewModel) : ViewModel() {
    val lstTranformItems = toTransformItems(vmDetail.transform.value).asObservable()
    val interimIndex = SimpleIntegerProperty()

    init {
    }

    override fun onCommit() {
        super.onCommit()
        vmDetail.transform.value = lstTranformItems.flatMap { listOf(it.extractor, it.replacement) }.joinToString("\n")
    }

}
