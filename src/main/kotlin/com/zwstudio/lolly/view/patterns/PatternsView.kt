package com.zwstudio.lolly.view.patterns

import com.zwstudio.lolly.data.patterns.PatternsViewModel
import tornadofx.*

class PatternsView : Fragment("Patterns in Language") {
    var vm = PatternsViewModel()

    override val root = vbox {
        tag = this@PatternsView
        toolbar {
            button("Add Pattern")
            button("Add Web Page")
            button("Refresh").action {
                vm.reload()
            }
        }
    }
}
