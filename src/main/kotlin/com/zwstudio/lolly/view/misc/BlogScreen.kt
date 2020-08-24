package com.zwstudio.lolly.view.misc

import tornadofx.Fragment
import tornadofx.button
import tornadofx.tag
import tornadofx.vbox

class BlogScreen : Fragment("Blog") {
    override val root = vbox {
        tag = this@BlogScreen
        button("Button 1")
        button("Button 2")
    }
}
