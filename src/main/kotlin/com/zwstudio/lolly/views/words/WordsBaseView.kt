package com.zwstudio.lolly.views.words

import com.zwstudio.lolly.models.misc.MDictionary
import com.zwstudio.lolly.models.wpp.MLangPhrase
import com.zwstudio.lolly.viewmodels.misc.SettingsViewModel
import com.zwstudio.lolly.viewmodels.phrases.PhrasesLangViewModel
import com.zwstudio.lolly.views.ILollySettings
import javafx.scene.Node
import javafx.scene.control.CheckBox
import javafx.scene.control.TabPane
import javafx.scene.control.TableView
import javafx.scene.control.ToolBar
import tornadofx.*

abstract class WordsPhraseBaseView(title: String? = null, icon: Node? = null) : Fragment(title, icon), ILollySettings {
    open var toolbarDicts: ToolBar by singleAssign()
    open var dictsPane: TabPane by singleAssign()
    abstract val vmSettings: SettingsViewModel

    override fun onSettingsChanged() {
        fun f(o: MDictionary) {
            val name = o.dictname
            val t = dictsPane.tabs.firstOrNull { it.text == name }
            if (t == null) {
                dictsPane.tab(find<WordsDictView>("dict" to o)) {
                    textProperty().unbind()
                    text = name
                }.setOnClosed {
                    val b = toolbarDicts.items.first { (it as CheckBox).text == name } as CheckBox
                    b.isSelected = false
                }
            } else
                dictsPane.tabs.remove(t)
        }

        toolbarDicts.items.clear()
        for (o in vmSettings.lstDictsReference) {
            toolbarDicts.checkbox {
                text = o.dictname
            }.action {
                f(o)
            }
        }
        dictsPane.tabs.clear()
        for (o in vmSettings.selectedDictsReference) {
            val b = toolbarDicts.items.first { (it as CheckBox).text == o.dictname } as CheckBox
            b.isSelected = true
            f(o)
        }
    }

    fun searchDict(word: String?) {
        val word = word ?: ""
        dictsPane.tabs.forEach {
            val f = it.content.tag as WordsDictView
            f.searchDict(word)
        }
    }
}

abstract class WordsBaseView(title: String? = null, icon: Node? = null) : WordsPhraseBaseView(title, icon) {
    var tvPhrases: TableView<MLangPhrase> by singleAssign()
    val vmPhrasesLang = PhrasesLangViewModel()
}
