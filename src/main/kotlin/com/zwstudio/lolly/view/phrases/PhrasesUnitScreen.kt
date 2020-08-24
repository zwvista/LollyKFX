package com.zwstudio.lolly.view.phrases

import tornadofx.Fragment
import tornadofx.button
import tornadofx.tag
import tornadofx.vbox

class PhrasesUnitScreen : Fragment("Phrases in Unit") {
    override val root = vbox {
        tag = this@PhrasesUnitScreen
        button("Button 1")
        button("Button 2")
    }
}
