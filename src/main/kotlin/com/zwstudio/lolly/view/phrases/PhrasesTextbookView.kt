package com.zwstudio.lolly.view.phrases

import com.zwstudio.lolly.data.phrases.PhrasesUnitDetailViewModel
import com.zwstudio.lolly.data.phrases.PhrasesUnitViewModel
import com.zwstudio.lolly.domain.wpp.MUnitPhrase
import javafx.geometry.Orientation
import javafx.scene.layout.Priority
import tornadofx.*

class PhrasesTextbookView : PhrasesBaseView("Phrases in Textbook") {
    var vm = PhrasesUnitViewModel(false)
    override val vmSettings get() = vm.vmSettings

    override val root = vbox {
        tag = this@PhrasesTextbookView
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
                column("PHRASE", MUnitPhrase::phraseProperty).makeEditable()
                column("TRANSLATION", MUnitPhrase::translationProperty).makeEditable()
                readonlyColumn("PHRASEID", MUnitPhrase::phraseid)
                readonlyColumn("ID", MUnitPhrase::id)
                onEditCommit {
                    val title = this.tableColumn.text
                    if (title == "Phrase")
                        rowValue.phrase = vmSettings.autoCorrectInput(rowValue.phrase)
                }
                onSelectionChange {
                    if (it == null) return@onSelectionChange
                }
                onDoubleClick {
                    // https://github.com/edvin/tornadofx/issues/226
                    val modal = find<PhrasesUnitDetailView>("vm" to PhrasesUnitDetailViewModel(selectionModel.selectedItem)) { openModal(block = true) }
                    if (modal.result)
                        this.refresh()
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
