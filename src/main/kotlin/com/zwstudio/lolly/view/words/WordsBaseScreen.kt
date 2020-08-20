package com.zwstudio.lolly.view.words

import com.zwstudio.lolly.data.SettingsViewModel
import com.zwstudio.lolly.view.ILollySettings
import javafx.scene.Node
import javafx.scene.control.ToolBar
import tornadofx.Fragment
import tornadofx.checkbox
import tornadofx.singleAssign

abstract class WordsBaseScreen(title: String? = null, icon: Node? = null) : Fragment(title, icon), ILollySettings {
    open var toolbarDicts: ToolBar by singleAssign()
    abstract val vmSettings: SettingsViewModel

    override fun onSettingsChanged() {
        toolbarDicts.items.clear()
        for (o in vmSettings.lstDictsReference) {
            toolbarDicts.checkbox {
                text = o.dictname
            }
        }
    }
}
