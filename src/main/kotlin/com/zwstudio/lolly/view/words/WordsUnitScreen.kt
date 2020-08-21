package com.zwstudio.lolly.view.words

import com.zwstudio.lolly.data.WordsUnitViewModel
import com.zwstudio.lolly.domain.MUnitWord
import javafx.geometry.Orientation
import javafx.scene.layout.Priority
import javafx.scene.web.WebView
import tornadofx.*

class WordsUnitScreen : WordsBaseScreen("Words in Unit") {
    var vm: WordsUnitViewModel = WordsUnitViewModel()
    override val vmSettings = vm.vmSettings

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
        splitpane(Orientation.HORIZONTAL) {
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
                    val title = this.tableColumn.text
                    if (title == "WORD") {
                        // https://stackoverflow.com/questions/29512142/how-do-i-restore-a-previous-value-in-javafx-tablecolumns-oneditcommit
                        rowValue.word = "ddddddddddd"
                        tableColumn.isVisible = false
                        tableColumn.isVisible = true
                    }
                }
                onSelectionChange {
                    dictsPane.tabs.forEach { (it.content as WebView) }
                }
            }
            splitpane(Orientation.VERTICAL) {
                dictsPane = tabpane {
                    vgrow = Priority.ALWAYS
                }
            }
        }
    }

    init {
        vm.getDataInTextbook().subscribe()
        onSettingsChanged()
    }
}
