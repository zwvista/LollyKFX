package com.zwstudio.lolly.view.words

import javafx.scene.Parent
import tornadofx.*

class WordsReviewScreen : Fragment("Words Review") {
    override val root = vbox {
        button("Button 1")
        button("Button 2")
    }
}
