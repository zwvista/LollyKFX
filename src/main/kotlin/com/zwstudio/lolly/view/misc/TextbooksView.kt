package com.zwstudio.lolly.view.misc

import tornadofx.Fragment
import tornadofx.button
import tornadofx.tag
import tornadofx.vbox

class TextbooksView : Fragment("Words in Unit") {
    override val root = vbox {
        tag = this@TextbooksView
        button("Button 1")
        button("Button 2")
    }
}
