package com.zwstudio.lolly.views.misc

import tornadofx.Fragment
import tornadofx.button
import tornadofx.tag
import tornadofx.vbox

class ReadNumberView : Fragment("Read Number") {
    override val root = vbox {
        tag = this@ReadNumberView
        button("Button 1")
        button("Button 2")
    }
}
