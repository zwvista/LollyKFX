package com.zwstudio.lolly.view.words

import tornadofx.Fragment
import tornadofx.button
import tornadofx.tag
import tornadofx.vbox

class WordsTextbookScreen : Fragment("Words in Textbook") {
    override val root = vbox {
        tag = this@WordsTextbookScreen
        button("Button 1")
        button("Button 2")
    }
}
