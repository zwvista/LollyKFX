package com.zwstudio.lolly.view.misc

import tornadofx.Fragment
import tornadofx.button
import tornadofx.tag
import tornadofx.vbox

class TextbooksScreen : Fragment("Words in Unit") {
    override val root = vbox {
        tag = this@TextbooksScreen
        button("Button 1")
        button("Button 2")
    }
}
