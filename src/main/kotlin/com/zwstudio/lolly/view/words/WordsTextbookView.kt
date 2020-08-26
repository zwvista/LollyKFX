package com.zwstudio.lolly.view.words

import com.zwstudio.lolly.data.words.WordsUnitViewModel
import com.zwstudio.lolly.domain.wpp.MUnitWord
import com.zwstudio.lolly.domain.wpp.UnitWordViewModel
import javafx.geometry.Orientation
import javafx.scene.control.ButtonBar
import javafx.scene.layout.Priority
import tornadofx.*

class WordsTextbookView : WordsBaseView("Words in Textbook") {
    var vm = WordsUnitViewModel(false)
    override val vmSettings get() = vm.vmSettings

    override val root = vbox {
        tag = this@WordsTextbookView
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
                column("WORD", MUnitWord::wordProperty).makeEditable()
                column("NOTE", MUnitWord::noteProperty).makeEditable()
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
                    }
                }
                onSelectionChange {
                    onWordChanged(it?.word)
                }
                onDoubleClick {
                    // https://github.com/edvin/tornadofx/issues/226
                    val modal = find<WordsUnitDetailView>("model" to UnitWordViewModel(selectionModel.selectedItem)) { openModal(block = true) }
                    if (modal.result == ButtonBar.ButtonData.OK_DONE)
                        this.refresh()
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
