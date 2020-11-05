package com.zwstudio.lolly.data.patterns

import com.zwstudio.lolly.domain.wpp.MPattern
import com.zwstudio.lolly.domain.wpp.MPatternVariation
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.ListChangeListener
import tornadofx.*

class PatternsSplitViewModel(item: MPattern) : ViewModel() {
    val id = SimpleIntegerProperty()
    val pattern  = SimpleStringProperty()
    val lstPatterns = listOf(item).asObservable()
    val lstPatternVariations = mutableListOf<MPatternVariation>().asObservable()

    init {
        val strs = item.pattern.split('／')
        lstPatternVariations.addAll(strs.mapIndexed {i, s -> MPatternVariation().apply { index = i + 1; variation = s }})
        lstPatternVariations.addListener(ListChangeListener {
            pattern.value = lstPatternVariations.map { it.variation }.distinct().joinToString("／")
        })
    }

    fun reindex(onNext: (Int) -> Unit) {
        for (i in 1..lstPatternVariations.size) {
            val item = lstPatternVariations[i - 1]
            if (item.index == i) continue
            item.index = i
            onNext(i - 1)
        }
    }

    override fun onCommit() {
        super.onCommit()
    }
}
