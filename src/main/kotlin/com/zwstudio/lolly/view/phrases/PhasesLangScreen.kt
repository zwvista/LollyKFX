package com.zwstudio.lolly.view.phrases

import tornadofx.Fragment
import tornadofx.button
import tornadofx.tag
import tornadofx.vbox

class PhasesLangScreen : Fragment("Phrases in Language") {
    override val root = vbox {
        tag = this@PhasesLangScreen
        button("Button 1")
        button("Button 2")
    }
}
