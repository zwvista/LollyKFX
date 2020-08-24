package com.zwstudio.lolly.view.phrases

import com.zwstudio.lolly.data.phrases.PhrasesLangViewModel
import com.zwstudio.lolly.domain.MLangPhrase
import javafx.geometry.Orientation
import javafx.scene.layout.Priority
import tornadofx.*

class PhrasesLangScreen : PhrasesBaseScreen("Phrases in Language") {
    var vm = PhrasesLangViewModel()
    override val vmSettings get() = vm.vmSettings

    override val root = vbox {
        tag = this@PhrasesLangScreen
        toolbar {
            button("Add")
            button("Refresh").action {
                vm.reload()
            }
        }
        splitpane(Orientation.HORIZONTAL) {
            vgrow = Priority.ALWAYS
            tableview(vm.lstPhrases) {
                readonlyColumn("ID", MLangPhrase::id)
                column("PHRASE", MLangPhrase::phrase).makeEditable()
                column("TRANSLATION", MLangPhrase::translationNotNull).makeEditable()
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
