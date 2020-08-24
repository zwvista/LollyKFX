package com.zwstudio.lolly.view.words

import tornadofx.Fragment
import tornadofx.button
import tornadofx.tag
import tornadofx.vbox

class WordsLangScreen : Fragment("Words in Language") {
    override val root = vbox {
        tag = this@WordsLangScreen
        button("Button 1")
        button("Button 2")
    }
}
