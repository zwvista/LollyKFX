package com.zwstudio.lolly.view.patterns

import javafx.scene.Parent
import tornadofx.*

class PatternsScreen : Fragment("Patterns in Language") {
    override val root = vbox {
        button("Button 1")
        button("Button 2")
    }
}
