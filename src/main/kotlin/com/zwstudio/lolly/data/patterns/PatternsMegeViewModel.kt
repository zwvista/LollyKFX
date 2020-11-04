package com.zwstudio.lolly.data.patterns

import com.zwstudio.lolly.data.misc.splitUsingCommaAndMerge
import com.zwstudio.lolly.domain.wpp.MPattern
import com.zwstudio.lolly.domain.wpp.MPatternVariation
import javafx.beans.property.SimpleStringProperty
import javafx.collections.ListChangeListener
import tornadofx.*

class PatternsMegeViewModel(val items: List<MPattern>) : ViewModel() {
    val pattern = SimpleStringProperty()
    val note = SimpleStringProperty()
    val tags = SimpleStringProperty()
    val lstPatterns = items.asObservable()
    val lstPatternVariations = mutableListOf<MPatternVariation>().asObservable()

    init {
        note.value = items.map { it.note }.splitUsingCommaAndMerge()
        tags.value = items.map { it.tags }.splitUsingCommaAndMerge()
        val strs = items.flatMap { it.pattern.split('／') }.sorted().distinct()
        lstPatternVariations.addAll(strs.mapIndexed {i, s -> MPatternVariation().apply { index.value = i + 1; variation.value = s }})
        lstPatternVariations.addListener(ListChangeListener {
            pattern.value = lstPatternVariations.map { it.variation }.distinct().joinToString("／")
        })
    }

    override fun onCommit() {
        super.onCommit()
    }
}
