package com.zwstudio.lolly.view.words

import com.zwstudio.lolly.data.words.WordsLangViewModel
import com.zwstudio.lolly.domain.wpp.LangWordViewModel
import com.zwstudio.lolly.domain.wpp.MLangWord
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
                column("NOTE", MLangWord::note).makeEditable()
                readonlyColumn("LEVEL", MLangWord::level)
                readonlyColumn("ACCURACY", MLangWord::accuracy)
                readonlyColumn("FAMIID", MLangWord::famiid)
                onEditCommit {
                    val title = this.tableColumn.text
                    if (title == "WORD") {
                        // https://stackoverflow.com/questions/29512142/how-do-i-restore-a-previous-value-in-javafx-tablecolumns-oneditcommit
                        rowValue.word = "ddddddddddd"
                    }
                }
                onSelectionChange {
                    onWordChanged(it?.word)
                }
                onDoubleClick {
                    // https://github.com/edvin/tornadofx/issues/226
                    find<WordsLangDetailView>("model" to LangWordViewModel(selectionModel.selectedItem)) { openModal() }
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
