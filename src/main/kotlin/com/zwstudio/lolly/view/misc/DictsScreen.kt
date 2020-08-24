package com.zwstudio.lolly.view.misc

import tornadofx.Fragment
import tornadofx.button
import tornadofx.tag
import tornadofx.vbox

class DictsScreen : Fragment("Dictionaries") {
    override val root = vbox {
        tag = this@DictsScreen
        button("Button 1")
        button("Button 2")
    }
}
