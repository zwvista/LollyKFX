package com.zwstudio.lolly.view.phrases

import tornadofx.Fragment
import tornadofx.button
import tornadofx.tag
import tornadofx.vbox

class PhrasesReviewView : Fragment("Phrases Review") {
    override val root = vbox {
        tag = this@PhrasesReviewView
        button("Button 1")
        button("Button 2")
    }
}
