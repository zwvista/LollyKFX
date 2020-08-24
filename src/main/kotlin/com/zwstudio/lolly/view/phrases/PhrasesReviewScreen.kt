package com.zwstudio.lolly.view.phrases

import tornadofx.Fragment
import tornadofx.button
import tornadofx.tag
import tornadofx.vbox

class PhrasesReviewScreen : Fragment("Phrases Review") {
    override val root = vbox {
        tag = this@PhrasesReviewScreen
        button("Button 1")
        button("Button 2")
    }
}
