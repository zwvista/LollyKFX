package com.zwstudio.lolly.view.words

import com.zwstudio.lolly.data.SettingsViewModel
import com.zwstudio.lolly.view.ILollySettings
import javafx.scene.Node
import javafx.scene.control.CheckBox
import javafx.scene.control.TabPane
import javafx.scene.control.ToolBar
import tornadofx.Fragment
import tornadofx.action
import tornadofx.checkbox
import tornadofx.singleAssign

abstract class WordsBaseScreen(title: String? = null, icon: Node? = null) : Fragment(title, icon), ILollySettings {
    open var toolbarDicts: ToolBar by singleAssign()
    open var dictsPane: TabPane by singleAssign()
    abstract val vmSettings: SettingsViewModel

    override fun onSettingsChanged() {
        fun f(name: String) {
            val t = dictsPane.tabs.firstOrNull { it.text == name }
            if (t == null) {
                dictsPane.tab<WordsDictScreen> {
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
                f(o.dictname)
            }
        }
        dictsPane.tabs.clear()
        for (o in vmSettings.selectedDictsReference) {
            val b = toolbarDicts.items.first { (it as CheckBox).text == o.dictname } as CheckBox
            b.isSelected = true
            f(o.dictname)
        }
    }
}
