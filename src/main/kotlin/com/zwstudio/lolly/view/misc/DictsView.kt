package com.zwstudio.lolly.view.misc

import tornadofx.Fragment
import tornadofx.button
import tornadofx.tag
import tornadofx.vbox

class DictsView : Fragment("Dictionaries") {
    override val root = vbox {
        tag = this@DictsView
        button("Button 1")
        button("Button 2")
    }
}
