package com.zwstudio.lolly.view.words

import com.zwstudio.lolly.data.WordsUnitViewModel
import com.zwstudio.lolly.domain.MUnitWord
import javafx.geometry.Orientation
import javafx.scene.control.SplitPane
import javafx.scene.control.ToolBar
import javafx.scene.layout.Priority
import tornadofx.*

class WordsUnitScreen : Fragment("Words in Unit") {
    var toolbarDicts: ToolBar by singleAssign()
    var splitPane: SplitPane by singleAssign()
    var vm: WordsUnitViewModel = WordsUnitViewModel()

    override val root = vbox {
        toolbarDicts = toolbar()
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
        splitPane = splitpane(Orientation.HORIZONTAL) {
            vgrow = Priority.ALWAYS
            tableview(vm.lstWords) {
                readonlyColumn("UNIT", MUnitWord::unitstr)
                readonlyColumn("PART", MUnitWord::partstr)
                readonlyColumn("SEQNUM", MUnitWord::seqnum)
                column("WORD", MUnitWord::word).makeEditable()
                column("NOTE", MUnitWord::noteNotNull).makeEditable()
                readonlyColumn("LEVEL", MUnitWord::level)
                readonlyColumn("ACCURACY", MUnitWord::accuracy)
                readonlyColumn("WORDID", MUnitWord::wordid)
                readonlyColumn("ID", MUnitWord::id)
                readonlyColumn("FAMIID", MUnitWord::famiid)
                onEditCommit {
                }
            }
            splitpane(Orientation.VERTICAL) {
                webview()
            }
        }
    }

    init {
        vm.getDataInTextbook().subscribe {
            toolbarDicts.items.clear()
            for (o in vm.vmSettings.lstDictsReference) {
                toolbarDicts.checkbox {
                    text = o.dictname
                }
            }
        }
    }
}
