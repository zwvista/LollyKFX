package com.zwstudio.lolly.viewmodels.dicts

import com.zwstudio.lolly.viewmodels.misc.applyTemplate
import com.zwstudio.lolly.viewmodels.misc.doTransform
import com.zwstudio.lolly.viewmodels.misc.toHtml
import com.zwstudio.lolly.viewmodels.misc.toTransformItems
import io.reactivex.rxjava3.core.Completable
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*

class TransformEditViewModel(val vmDetail: DictsDetailViewModel) : ViewModel() {
    val sourceWord = SimpleStringProperty("")
    var sourceUrl = ""
    val sourceText = SimpleStringProperty("")
    val resultText = SimpleStringProperty("")
    var resultHtml = ""
    val intermediateText = SimpleStringProperty("")
    var intermediateMaxIndex = 0
    val intermediateIndex = SimpleObjectProperty<Int>()
    val lstTranformItems = toTransformItems(vmDetail.transform.value).asObservable()
    var intermediateResults = mutableListOf<String>()

    init {
        intermediateIndex.addListener { _, _, newValue ->
            intermediateText.value = intermediateResults[newValue]
        }
    }

    override fun onCommit() {
        super.onCommit()
        vmDetail.transform.value = lstTranformItems.flatMap { listOf(it.extractor, it.replacement) }.joinToString("\r\n")
    }

    fun getHtml(): Completable {
        sourceUrl = vmDetail.url.value.replace("{0}", sourceWord.value)
        return vmDetail.vm.vmSettings.getHtml(sourceUrl).flatMapCompletable { sourceText.value = it; Completable.complete() }
    }

    fun transformText() {
        var text = sourceText.value
        intermediateResults = mutableListOf(text)
        for (item in lstTranformItems) {
            text = doTransform(text, item)
            intermediateResults.add(text)
        }
        intermediateIndex.value = 0
        intermediateMaxIndex = intermediateResults.size - 1
        resultText.value = text
        val t = vmDetail.template.value
        resultHtml = if (t.isEmpty()) toHtml(text) else applyTemplate(t, sourceWord.value, text)
    }

    fun reindex(onNext: (Int) -> Unit) {
        for (i in 1..lstTranformItems.size) {
            val item = lstTranformItems[i - 1]
            if (item.index == i) continue
            item.index = i
            onNext(i - 1)
        }
    }

}
