package com.zwstudio.lolly.view.words

import com.zwstudio.lolly.data.WordsUnitViewModel
import javafx.geometry.Orientation
import javafx.scene.control.ToolBar
import tornadofx.*

class WordsUnitScreen : Fragment("Words in Unit") {
    var toolbarDicts: ToolBar by singleAssign()
    lateinit var vm: WordsUnitViewModel

    override val root = vbox {
        toolbarDicts = toolbar {

        }
        toolbar {
            button("Add")
            button("Refresh")
            button("Batch")
            button("Toggle")
            button("Previous")
            checkbox("If Empty")
            button("Get Notes")
            button("Clear Notes")
            button("Review")
        }
        splitpane(Orientation.HORIZONTAL) {

        }
    }

    init {
        vm = WordsUnitViewModel()
    }
}
