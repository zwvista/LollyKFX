package com.zwstudio.lolly.ui.phrases

import com.zwstudio.lolly.viewmodels.misc.SettingsViewModel
import com.zwstudio.lolly.viewmodels.misc.copyText
import com.zwstudio.lolly.viewmodels.misc.googleString
import com.zwstudio.lolly.viewmodels.phrases.PhrasesLangDetailViewModel
import com.zwstudio.lolly.viewmodels.phrases.PhrasesLangViewModel
import com.zwstudio.lolly.viewmodels.phrases.PhrasesLinkViewModel
import com.zwstudio.lolly.viewmodels.words.WordsLangDetailViewModel
import com.zwstudio.lolly.models.wpp.MLangPhrase
import com.zwstudio.lolly.models.wpp.MLangWord
import com.zwstudio.lolly.ui.words.WordsLangDetailView
import com.zwstudio.lolly.ui.words.WordsLinkView
import javafx.application.Platform
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
        toolbarDicts = toolbar()
        toolbar {
            button("Add").action {
                val modal = find<PhrasesLangDetailView>("vmDetail" to PhrasesLangDetailViewModel(vm, vm.newLangPhrase())) { openModal(block = true) }
                if (modal.result) {
                    vm.lstPhrases.add(modal.vmDetail.item)
                    tvPhrases.refresh()
                }
            }
            button("Refresh").action {
                vm.reload()
            }
            choicebox(vm.scopeFilter, SettingsViewModel.lstScopePhraseFilters)
            textfield(vm.textFilter) {
                promptText = "Filter"
            }
        }
        splitpane(Orientation.HORIZONTAL) {
            vgrow = Priority.ALWAYS
            vbox {
                splitpane(Orientation.VERTICAL) {
                    vgrow = Priority.ALWAYS
                    setDividerPosition(0, 0.8)
                    tvPhrases = tableview(vm.lstPhrases) {
                        vgrow = Priority.ALWAYS
                        readonlyColumn("ID", MLangPhrase::id)
                        column("PHRASE", MLangPhrase::phraseProperty).makeEditable()
                        column("TRANSLATION", MLangPhrase::translationProperty).makeEditable()
                        onEditCommit {
                            val title = this.tableColumn.text
                            if (title == "Phrase")
                                rowValue.phrase = vmSettings.autoCorrectInput(rowValue.phrase)
                            vm.update(rowValue).subscribe()
                        }
                        onSelectionChange {
                            vmWordsLang.getWords(it?.id).subscribe {
                                if (vmWordsLang.lstWords.isNotEmpty())
                                    Platform.runLater {
                                        tvWords.selectionModel.select(0)
                                    }
                            }
                        }
                        fun edit() {
                            // https://github.com/edvin/tornadofx/issues/226
                            val modal = find<PhrasesLangDetailView>("vmDetail" to PhrasesLangDetailViewModel(vm, selectedItem!!)) { openModal(block = true) }
                            if (modal.result)
                                this.refresh()
                        }
                        fun link() {
                            val o = selectedItem!!
                            val modal = find<WordsLinkView>("vm" to PhrasesLinkViewModel(o.id, o.phrase)) { openModal(block = true) }
                            if (modal.result)
                                tvWords.refresh()
                        }
                        var isAltDown = false
                        setOnMousePressed {
                            isAltDown = it.isAltDown
                        }
                        onDoubleClick {
                            if (isAltDown)
                                link()
                            else
                                edit()
                        }
                        contextmenu {
                            item("Edit").action {
                                edit()
                            }
                            item("Link Existing Words").action {
                                link()
                            }
                            separator()
                            item("Delete")
                            separator()
                            item("Copy").action {
                                copyText(selectedItem?.phrase)
                            }
                            item("Google").action {
                                googleString(selectedItem?.phrase)
                            }
                            items.forEach {
                                it.enableWhen { selectionModel.selectedItemProperty().isNotNull }
                            }
                        }
                    }
                    tvWords = tableview(vmWordsLang.lstWords) {
                        readonlyColumn("ID", MLangWord::id)
                        column("WORD", MLangWord::wordProperty).makeEditable()
                        column("NOTE", MLangWord::noteProperty).makeEditable()
                        readonlyColumn("ACCURACY", MLangWord::accuracy)
                        readonlyColumn("FAMIID", MLangWord::famiid)
                        onEditCommit {
                            val title = this.tableColumn.text
                            if (title == "WORD")
                                rowValue.word = vmSettings.autoCorrectInput(rowValue.word)
                            vmWordsLang.update(rowValue).subscribe()
                        }
                        onSelectionChange {
                            searchDict(it?.word)
                        }
                        fun edit() {
                            // https://github.com/edvin/tornadofx/issues/226
                            val modal = find<WordsLangDetailView>("vmDetail" to WordsLangDetailViewModel(vmWordsLang, selectedItem!!)) { openModal(block = true) }
                            if (modal.result)
                                this.refresh()
                        }
                        onDoubleClick {
                            edit()
                        }
                        contextmenu {
                            item("Edit").action {
                                edit()
                            }
                            separator()
                            item("Unlink").action {
                                vmWordsLang.unlink(selectedItem!!.id, tvPhrases.selectedItem!!.id)
                            }
                            separator()
                            item("Copy").action {
                                copyText(selectedItem?.word)
                            }
                            item("Google").action {
                                googleString(selectedItem?.word)
                            }
                            items.forEach {
                                it.enableWhen { selectionModel.selectedItemProperty().isNotNull }
                            }
                        }
                    }
                }
                label(vm.statusText)
            }
            dictsPane = tabpane {
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
