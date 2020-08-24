package com.zwstudio.lolly.view.words

import com.zwstudio.lolly.data.words.WordsUnitViewModel
import com.zwstudio.lolly.domain.MUnitWord
import javafx.geometry.Orientation
import javafx.scene.layout.Priority
import tornadofx.*

class WordsTextbookScreen : WordsBaseScreen("Words in Textbook") {
    var vm = WordsUnitViewModel(false)
    override val vmSettings get() = vm.vmSettings

    override val root = vbox {
        tag = this@WordsTextbookScreen
        toolbarDicts = toolbar()
        toolbar {
            button("Refresh").action {
                vm.reload()
            }
        }
        splitpane(Orientation.HORIZONTAL) {
            vgrow = Priority.ALWAYS
            tableview(vm.lstWords) {
                readonlyColumn("TEXTBOOKNAME", MUnitWord::textbookname)
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
                    if (it == null) return@onSelectionChange
                    val word = it.word
                    dictsPane.tabs.forEach {
                        val f = it.content.tag as WordsDictScreen
                        f.searchWord(word)
                    }
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
        onSettingsChanged()
    }

    override fun onSettingsChanged() {
        vm.reload()
        super.onSettingsChanged()
    }
}
