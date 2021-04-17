package com.zwstudio.lolly.views.phrases

import com.zwstudio.lolly.viewmodels.misc.SettingsViewModel
import com.zwstudio.lolly.viewmodels.misc.copyText
import com.zwstudio.lolly.viewmodels.misc.googleString
import com.zwstudio.lolly.viewmodels.phrases.PhrasesLinkViewModel
import com.zwstudio.lolly.viewmodels.phrases.PhrasesUnitDetailViewModel
import com.zwstudio.lolly.viewmodels.phrases.PhrasesUnitViewModel
import com.zwstudio.lolly.viewmodels.words.WordsLangDetailViewModel
import com.zwstudio.lolly.models.wpp.MLangWord
import com.zwstudio.lolly.models.wpp.MUnitPhrase
import com.zwstudio.lolly.views.words.WordsLangDetailView
import com.zwstudio.lolly.views.words.WordsLinkView
import javafx.application.Platform
import javafx.geometry.Orientation
import javafx.scene.control.TableView
import javafx.scene.layout.Priority
import tornadofx.*

class PhrasesTextbookView : PhrasesBaseView("Phrases in Textbook") {
    var tvPhrases: TableView<MUnitPhrase> by singleAssign()
    var vm = PhrasesUnitViewModel(false)
    override val vmSettings get() = vm.vmSettings

    override val root = vbox {
        tag = this@PhrasesTextbookView
        toolbarDicts = toolbar()
        toolbar {
            button("Refresh").action {
                vm.reload()
            }
            choicebox(vm.scopeFilter, SettingsViewModel.lstScopePhraseFilters)
            textfield(vm.textFilter) {
                promptText = "Filter"
            }
            combobox(vm.textbookFilter, vmSettings.lstTextbookFilters)
        }
        splitpane(Orientation.HORIZONTAL) {
            vgrow = Priority.ALWAYS
            vbox {
                splitpane(Orientation.VERTICAL) {
                    vgrow = Priority.ALWAYS
                    setDividerPosition(0, 0.8)
                    tvPhrases = tableview(vm.lstPhrases) {
                        vgrow = Priority.ALWAYS
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
                            vm.update(rowValue).subscribe()
                        }
                        onSelectionChange {
                            vmWordsLang.getWords(it?.phraseid).subscribe {
                                if (vmWordsLang.lstWords.isNotEmpty())
                                    Platform.runLater {
                                        tvWords.selectionModel.select(0)
                                    }
                            }
                        }
                        fun edit() {
                            // https://github.com/edvin/tornadofx/issues/226
                            val modal = find<PhrasesTextbookDetailView>("vmDetail" to PhrasesUnitDetailViewModel(vm, selectedItem!!)) { openModal(block = true) }
                            if (modal.result)
                                this.refresh()
                        }
                        fun link() {
                            val o = selectedItem!!
                            val modal = find<WordsLinkView>("vm" to PhrasesLinkViewModel(o.phraseid, o.phrase)) { openModal(block = true) }
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
                            item("Link Existing Words").action {
                                edit()
                            }
                            separator()
                            item("Unlink").action {
                                vmWordsLang.unlink(selectedItem!!.id, tvPhrases.selectedItem!!.phraseid)
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
                            onEditCommit {
                                val title = this.tableColumn.text
                                if (title == "WORD")
                                    rowValue.word = vmSettings.autoCorrectInput(rowValue.word)
                                vmWordsLang.update(rowValue).subscribe()
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
