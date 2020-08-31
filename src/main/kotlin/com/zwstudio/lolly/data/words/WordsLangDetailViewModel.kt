package com.zwstudio.lolly.data.words

import com.zwstudio.lolly.domain.wpp.MLangWord
import tornadofx.ItemViewModel

class WordsLangDetailViewModel(item: MLangWord) : ItemViewModel<MLangWord>(item) {
    val id = bind(MLangWord::id)
    val word = bind(MLangWord::wordProperty)
    val note = bind(MLangWord::noteProperty)
    val famiid = bind(MLangWord::famiid)
    val level = bind(MLangWord::level)
    val accuracy = bind(MLangWord::accuracy)
    val vmSingle = SingleWordViewModel(item.word)
}
