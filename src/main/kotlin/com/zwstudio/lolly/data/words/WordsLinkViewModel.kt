package com.zwstudio.lolly.data.words

import com.zwstudio.lolly.domain.wpp.MLangWord

class WordsLinkViewModel(val phraseid: Int, textFilter: String) : WordsLangViewModel() {

    init {
        this.textFilter.value = textFilter
    }

    override fun applyFilters() {
        super.applyFilters()
        for (o in lstWordsAll)
            o.isChecked.value = false
    }

    fun commit() {
        for (o in lstWordsAll)
            if (o.isChecked.value)
                wordPhraseService.link(o.id, phraseid).subscribe()
    }

    fun checkItems(n: Int, selectedItems: List<MLangWord>) {
        for (o in lstWordsAll)
            o.isChecked.value = when (n) {
                0 -> true
                1 -> false
                else -> if (!selectedItems.contains(o)) o.isChecked.value else n == 2
            }
    }
}
