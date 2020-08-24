package com.zwstudio.lolly.view.misc

import tornadofx.Fragment
import tornadofx.button
import tornadofx.tag
import tornadofx.vbox

class ReadNumberScreen : Fragment("Read Number") {
    override val root = vbox {
        tag = this@ReadNumberScreen
        button("Button 1")
        button("Button 2")
    }
}
