package com.zwstudio.lolly.view.misc

import com.zwstudio.lolly.data.patterns.PatternsViewModel
import tornadofx.*

class BlogView : Fragment("Blog") {
    var vm = PatternsViewModel()


    override val root = vbox {
        tag = this@BlogView
        toolbar {
            button("HtmlToMarked")
            button("Add B")
            button("Add I")
            button("Remove BI")
            button("Exchange BI")
            button("AddExplanation")
            button("MarkedToHtml")
            button("PatternToHtml")
            textfield("HtmlToMarked")
            button("PatternMarkDown")
            textfield("HtmlToMarked")
            button("AddNotesCommand")
        }
    }
}
