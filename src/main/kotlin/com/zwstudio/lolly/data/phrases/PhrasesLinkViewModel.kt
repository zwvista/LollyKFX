package com.zwstudio.lolly.data.phrases

import com.zwstudio.lolly.domain.wpp.MLangPhrase
import tornadofx.*

class PhrasesLinkViewModel(val wordid: Int, textFilter: String) : ViewModel() {
    val vm = PhrasesLangViewModel()

    init {
        vm.textFilter.value = textFilter
    }

    fun reload() {
        vm.reload()
        for (o in vm.lstPhrasesAll)
            o.isChecked.value = false
    }

    override fun onCommit() {
        super.onCommit()
        for (o in vm.lstPhrasesAll)
            if (o.isChecked.value)
                vm.wordPhraseService.link(wordid, o.id).subscribe()
    }

    fun checkItems(n: Int, selectedItems: List<MLangPhrase>) {
        for (o in vm.lstPhrasesAll)
            o.isChecked.value = when (n) {
                0 -> true
                1 -> false
                else -> if (!selectedItems.contains(o)) o.isChecked.value else n == 2
            }
    }
}
