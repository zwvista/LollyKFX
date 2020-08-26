package com.zwstudio.lolly.view.patterns

import tornadofx.Fragment
import tornadofx.button
import tornadofx.tag
import tornadofx.vbox

class PatternsView : Fragment("Patterns in Language") {
    override val root = vbox {
        tag = this@PatternsView
        button("Button 1")
        button("Button 2")
    }
}
