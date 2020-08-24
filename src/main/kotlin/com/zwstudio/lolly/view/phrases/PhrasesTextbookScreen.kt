package com.zwstudio.lolly.view.phrases

import com.zwstudio.lolly.data.phrases.PhrasesUnitViewModel
import com.zwstudio.lolly.domain.MUnitPhrase
import javafx.geometry.Orientation
import javafx.scene.layout.Priority
import tornadofx.*

class PhrasesTextbookScreen : PhrasesBaseScreen("Phrases in Textbook") {
    var vm = PhrasesUnitViewModel(false)
    override val vmSettings get() = vm.vmSettings

    override val root = vbox {
        tag = this@PhrasesTextbookScreen
        toolbar {
            button("Refresh").action {
                vm.reload()
            }
        }
        splitpane(Orientation.HORIZONTAL) {
            vgrow = Priority.ALWAYS
            tableview(vm.lstPhrases) {
                readonlyColumn("TEXTBOOKNAME", MUnitPhrase::textbookname)
                readonlyColumn("UNIT", MUnitPhrase::unitstr)
                readonlyColumn("PART", MUnitPhrase::partstr)
                readonlyColumn("SEQNUM", MUnitPhrase::seqnum)
                column("PHRASE", MUnitPhrase::phrase).makeEditable()
                column("TRANSLATION", MUnitPhrase::translationNotNull).makeEditable()
                readonlyColumn("PHRASEID", MUnitPhrase::phraseid)
                readonlyColumn("ID", MUnitPhrase::id)
                onEditCommit {
                    val title = this.tableColumn.text
                    if (title == "Phrase") {
                        // https://stackoverflow.com/questions/29512142/how-do-i-restore-a-previous-value-in-javafx-tablecolumns-oneditcommit
                        rowValue.phrase = "ddddddddddd"
                        tableColumn.isVisible = false
                        tableColumn.isVisible = true
                    }
                }
                onSelectionChange {
                    if (it == null) return@onSelectionChange
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
