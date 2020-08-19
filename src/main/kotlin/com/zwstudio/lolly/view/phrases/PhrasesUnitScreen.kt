package com.zwstudio.lolly.view.phrases

import javafx.scene.Parent
import tornadofx.*

class PhrasesUnitScreen : Fragment("Phrases in Unit") {
    override val root = vbox {
        button("Button 1")
        button("Button 2")
    }
}
