package com.zwstudio.lolly.view.words

import tornadofx.Fragment
import tornadofx.button
import tornadofx.tag
import tornadofx.vbox

class WordsReviewView : Fragment("Words Review") {
    override val root = vbox {
        tag = this@WordsReviewView
        button("Button 1")
        button("Button 2")
    }
}
