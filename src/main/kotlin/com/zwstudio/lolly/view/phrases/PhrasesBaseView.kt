package com.zwstudio.lolly.view.phrases

import com.zwstudio.lolly.data.words.WordsLangViewModel
import com.zwstudio.lolly.domain.wpp.MLangWord
import com.zwstudio.lolly.view.words.WordsPhraseBaseView
import javafx.scene.Node
import javafx.scene.control.TableView
import tornadofx.*

abstract class PhrasesBaseView(title: String? = null, icon: Node? = null) : WordsPhraseBaseView(title, icon) {
    var tvWords: TableView<MLangWord> by singleAssign()
    val vmWordsLang = WordsLangViewModel()

}
