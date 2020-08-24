package com.zwstudio.lolly.view.words

import tornadofx.Fragment
import tornadofx.button
import tornadofx.tag
import tornadofx.vbox

class WordsReviewScreen : Fragment("Words Review") {
    override val root = vbox {
        tag = this@WordsReviewScreen
        button("Button 1")
        button("Button 2")
    }
}
