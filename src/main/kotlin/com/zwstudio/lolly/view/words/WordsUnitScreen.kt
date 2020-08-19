package com.zwstudio.lolly.view.words

import javafx.scene.Parent
import tornadofx.*

class WordsUnitScreen : Fragment("Words in Unit") {
    override val root = vbox {
        button("Button 1")
        button("Button 2")
    }
}
