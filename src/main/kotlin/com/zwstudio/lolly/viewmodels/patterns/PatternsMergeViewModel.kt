package com.zwstudio.lolly.viewmodels.patterns

import com.zwstudio.lolly.viewmodels.misc.splitUsingCommaAndMerge
import com.zwstudio.lolly.models.wpp.MPattern
import com.zwstudio.lolly.models.wpp.MPatternVariation
import com.zwstudio.lolly.services.wpp.PatternService
import javafx.beans.property.SimpleStringProperty
import javafx.collections.ListChangeListener
import tornadofx.*

class PatternsMergeViewModel(val items: List<MPattern>) : ViewModel() {
    val pattern = SimpleStringProperty()
    val note = SimpleStringProperty()
    val tags = SimpleStringProperty()
    val lstPatterns = items.asObservable()
    val lstPatternVariations = mutableListOf<MPatternVariation>().asObservable()

    private val patternService = PatternService()

    init {
        note.value = items.map { it.note }.splitUsingCommaAndMerge()
        tags.value = items.map { it.tags }.splitUsingCommaAndMerge()
        val strs = items.flatMap { it.pattern.split('／') }.sorted().distinct()
        lstPatternVariations.addAll(strs.mapIndexed {i, s -> MPatternVariation().apply { index = i + 1; variation = s }})
        lstPatternVariations.addListener(ListChangeListener {
            mergeVariations()
        })
        mergeVariations()
    }

    fun mergeVariations() {
        pattern.value = lstPatternVariations.map { it.variation }.distinct().joinToString("／")
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
        val o = MPattern()
        o.idsMerge = lstPatterns.map { it.id }.sorted().joinToString(",")
        o.pattern = pattern.value
        o.note = note.value
        o.tags = tags.value
        patternService.mergePatterns(o)
    }
}
