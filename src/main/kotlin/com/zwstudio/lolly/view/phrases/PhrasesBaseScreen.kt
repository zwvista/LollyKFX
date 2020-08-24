package com.zwstudio.lolly.view.phrases

import com.zwstudio.lolly.data.SettingsViewModel
import com.zwstudio.lolly.view.ILollySettings
import javafx.scene.Node
import tornadofx.Fragment

abstract class PhrasesBaseScreen(title: String? = null, icon: Node? = null) : Fragment(title, icon), ILollySettings {
    abstract val vmSettings: SettingsViewModel

    override fun onSettingsChanged() {
    }
}
