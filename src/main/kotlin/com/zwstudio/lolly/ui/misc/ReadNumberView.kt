package com.zwstudio.lolly.ui.misc

import tornadofx.*

class ReadNumberView : Fragment("Read Number") {
    override val root = vbox {
        tag = this@ReadNumberView
        button("Button 1")
        button("Button 2")
    }
}