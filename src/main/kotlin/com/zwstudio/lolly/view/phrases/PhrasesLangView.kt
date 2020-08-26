package com.zwstudio.lolly.view.phrases

import com.zwstudio.lolly.data.phrases.PhrasesLangViewModel
import com.zwstudio.lolly.domain.wpp.LangPhraseViewModel
import com.zwstudio.lolly.domain.wpp.MLangPhrase
import javafx.geometry.Orientation
import javafx.scene.layout.Priority
import tornadofx.*

class PhrasesLangView : PhrasesBaseView("Phrases in Language") {
    var vm = PhrasesLangViewModel()
    override val vmSettings get() = vm.vmSettings

    override val root = vbox {
        tag = this@PhrasesLangView
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
                column("PHRASE", MLangPhrase::phraseProperty).makeEditable()
                column("TRANSLATION", MLangPhrase::translationProperty).makeEditable()
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
                    val modal = find<PhrasesLangDetailView>("model" to LangPhraseViewModel(selectionModel.selectedItem)) { openModal(block = true) }
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
