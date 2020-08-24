package com.zwstudio.lolly.view.words

import com.zwstudio.lolly.data.words.WordsLangViewModel
import com.zwstudio.lolly.domain.MLangWord
import javafx.geometry.Orientation
import javafx.scene.layout.Priority
import tornadofx.*

class WordsLangScreen : WordsBaseScreen("Words in Language") {
    var vm = WordsLangViewModel()
    override val vmSettings get() = vm.vmSettings

    override val root = vbox {
        tag = this@WordsLangScreen
        toolbarDicts = toolbar()
        toolbar {
            button("Add")
            button("Refresh").action {
                vm.reload()
            }
        }
        splitpane(Orientation.HORIZONTAL) {
            vgrow = Priority.ALWAYS
            tableview(vm.lstWords) {
                readonlyColumn("ID", MLangWord::id)
                column("WORD", MLangWord::word).makeEditable()
                column("NOTE", MLangWord::noteNotNull).makeEditable()
                readonlyColumn("LEVEL", MLangWord::level)
                readonlyColumn("ACCURACY", MLangWord::accuracy)
                readonlyColumn("FAMIID", MLangWord::famiid)
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
