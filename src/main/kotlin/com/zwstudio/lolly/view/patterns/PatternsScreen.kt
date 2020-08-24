package com.zwstudio.lolly.view.patterns

import tornadofx.Fragment
import tornadofx.button
import tornadofx.tag
import tornadofx.vbox

class PatternsScreen : Fragment("Patterns in Language") {
    override val root = vbox {
        tag = this@PatternsScreen
        button("Button 1")
        button("Button 2")
    }
}
