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
        lstPatternVariations.addAll(strs.mapIndexed {i, s -> MPatternVariation().apply { index.value = i + 1; variation.value = s }})
        lstPatternVariations.addListener(ListChangeListener {
            pattern.value = lstPatternVariations.map { it.variation }.distinct().joinToString("／")
        })
    }

    override fun onCommit() {
        super.onCommit()
    }
}
