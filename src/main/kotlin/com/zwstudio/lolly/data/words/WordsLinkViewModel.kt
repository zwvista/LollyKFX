package com.zwstudio.lolly.data.words

import com.zwstudio.lolly.domain.wpp.MLangWord
import tornadofx.*

class WordsLinkViewModel(val phraseid: Int, textFilter: String) : ViewModel() {
    val vm = WordsLangViewModel()

    init {
        vm.textFilter.value = textFilter
    }

    fun reload() {
        vm.reload()
        for (o in vm.lstWordsAll)
            o.isChecked.value = false
    }

    override fun onCommit() {
        super.onCommit()
        for (o in vm.lstWordsAll)
            if (o.isChecked.value)
                vm.wordPhraseService.link(o.id, phraseid).subscribe()
    }

    fun checkItems(n: Int, selectedItems: List<MLangWord>) {
        for (o in vm.lstWordsAll)
            o.isChecked.value = when (n) {
                0 -> true
                1 -> false
                else -> if (!selectedItems.contains(o)) o.isChecked.value else n == 2
            }
    }
}
