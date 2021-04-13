package com.zwstudio.lolly.viewmodels.phrases

import com.zwstudio.lolly.models.wpp.MLangPhrase

class PhrasesLinkViewModel(val wordid: Int, textFilter: String) : PhrasesLangViewModel() {

    init {
        this.textFilter.value = textFilter
    }

    override fun applyFilters() {
        super.applyFilters()
        for (o in lstPhrasesAll)
            o.isChecked.value = false
    }

    fun commit() {
        for (o in lstPhrasesAll)
            if (o.isChecked.value)
                wordPhraseService.link(wordid, o.id).subscribe()
    }

    fun checkItems(n: Int, selectedItems: List<MLangPhrase>) {
        for (o in lstPhrasesAll)
            o.isChecked.value = when (n) {
                0 -> true
                1 -> false
                else -> if (!selectedItems.contains(o)) o.isChecked.value else n == 2
            }
    }
}
