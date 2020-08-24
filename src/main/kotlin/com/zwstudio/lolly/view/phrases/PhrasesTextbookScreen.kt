package com.zwstudio.lolly.view.phrases

import tornadofx.Fragment
import tornadofx.button
import tornadofx.tag
import tornadofx.vbox

class PhrasesTextbookScreen : Fragment("Phrases in Textbook") {
    override val root = vbox {
        tag = this@PhrasesTextbookScreen
        button("Button 1")
        button("Button 2")
    }
}
