package com.zwstudio.lolly.view.phrases

import com.zwstudio.lolly.data.phrases.PhrasesLangDetailViewModel
import com.zwstudio.lolly.data.phrases.PhrasesLangViewModel
import com.zwstudio.lolly.domain.wpp.MLangPhrase
import javafx.geometry.Orientation
import javafx.scene.control.TableView
import javafx.scene.layout.Priority
import tornadofx.*

class PhrasesLangView : PhrasesBaseView("Phrases in Language") {
    var tvPhrases: TableView<MLangPhrase> by singleAssign()
    var vm = PhrasesLangViewModel()
    override val vmSettings get() = vm.vmSettings

    override val root = vbox {
        tag = this@PhrasesLangView
        toolbar {
            button("Add").action {
                val modal = find<PhrasesLangDetailView>("vmDetail" to PhrasesLangDetailViewModel(vm.newLangPhrase())) { openModal(block = true) }
                if (modal.result) {
                    vm.lstPhrases.add(modal.vmDetail.item)
                    tvPhrases.refresh()
                }
            }
            button("Refresh").action {
                vm.reload()
            }
            choicebox(vm.scopeFilter, vmSettings.lstScopePhraseFilters)
            textfield(vm.textFilter) {
                promptText = "Filter"
            }
        }
        splitpane(Orientation.HORIZONTAL) {
            vgrow = Priority.ALWAYS
            tvPhrases = tableview(vm.lstPhrases) {
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
                    val modal = find<PhrasesLangDetailView>("vmDetail" to PhrasesLangDetailViewModel(selectionModel.selectedItem)) { openModal(block = true) }
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
