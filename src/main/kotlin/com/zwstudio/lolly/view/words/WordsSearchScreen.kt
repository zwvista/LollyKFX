package com.zwstudio.lolly.view.words

import tornadofx.Fragment
import tornadofx.button
import tornadofx.tag
import tornadofx.vbox

class WordsSearchScreen : Fragment("Search") {
    override val root = vbox {
        tag = this@WordsSearchScreen
        button("Button 1")
        button("Button 2")
    }
}
