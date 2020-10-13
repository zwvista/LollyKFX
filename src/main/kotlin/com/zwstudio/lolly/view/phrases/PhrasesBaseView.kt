package com.zwstudio.lolly.view.phrases

import com.zwstudio.lolly.data.misc.SettingsViewModel
import com.zwstudio.lolly.view.ILollySettings
import javafx.scene.Node
import javafx.scene.control.ToolBar
import tornadofx.*

abstract class PhrasesBaseView(title: String? = null, icon: Node? = null) : Fragment(title, icon), ILollySettings {
    open var toolbarDicts: ToolBar by singleAssign()
    abstract val vmSettings: SettingsViewModel

    override fun onSettingsChanged() {
    }
}
