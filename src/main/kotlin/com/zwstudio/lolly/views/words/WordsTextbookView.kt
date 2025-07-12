package com.zwstudio.lolly.views.words

import com.zwstudio.lolly.common.copyText
import com.zwstudio.lolly.common.googleString
import com.zwstudio.lolly.models.wpp.MLangPhrase
import com.zwstudio.lolly.models.wpp.MUnitWord
import com.zwstudio.lolly.viewmodels.misc.SettingsViewModel
import com.zwstudio.lolly.viewmodels.phrases.PhrasesLangDetailViewModel
import com.zwstudio.lolly.viewmodels.phrases.PhrasesLinkViewModel
import com.zwstudio.lolly.viewmodels.words.WordsUnitDetailViewModel
import com.zwstudio.lolly.viewmodels.words.WordsUnitViewModel
import com.zwstudio.lolly.views.phrases.PhrasesLangDetailView
import com.zwstudio.lolly.views.phrases.PhrasesLinkView
import javafx.geometry.Orientation
import javafx.scene.control.TableView
import javafx.scene.layout.Priority
import tornadofx.*

class WordsTextbookView : WordsBaseView("Words in Textbook") {
    var tvWords: TableView<MUnitWord> by singleAssign()
    var vm = WordsUnitViewModel(false)
    override val vmSettings get() = vm.vmSettings

    override val root = vbox {
        tag = this@WordsTextbookView
        toolbarDicts = toolbar()
        toolbar {
            button("Refresh").action {
                vm.reload()
            }
            choicebox(vm.scopeFilter, SettingsViewModel.lstScopeWordFilters)
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
                    tvWords = tableview(vm.lstWords) {
                        vgrow = Priority.ALWAYS
                        readonlyColumn("TEXTBOOKNAME", MUnitWord::textbookname)
                        readonlyColumn("UNIT", MUnitWord::unitstr)
                        readonlyColumn("PART", MUnitWord::partstr)
                        readonlyColumn("SEQNUM", MUnitWord::seqnum)
                        column("WORD", MUnitWord::wordProperty).makeEditable()
                        column("NOTE", MUnitWord::noteProperty).makeEditable()
                        readonlyColumn("ACCURACY", MUnitWord::accuracy)
                        readonlyColumn("WORDID", MUnitWord::wordid)
                        readonlyColumn("ID", MUnitWord::id)
                        readonlyColumn("FAMIID", MUnitWord::famiid)
                        onEditCommit {
                            val title = this.tableColumn.text
                            if (title == "WORD")
                                rowValue.word = vmSettings.autoCorrectInput(rowValue.word)
                            vm.update(rowValue).subscribe()
                        }
                        onSelectionChange {
                            searchDict(it?.word)
                            vmPhrasesLang.getPhrases(it?.wordid).subscribe()
                        }
                        fun edit() {
                            // https://github.com/edvin/tornadofx/issues/226
                            val modal = find<WordsTextbookDetailView>("vmDetail" to WordsUnitDetailViewModel(vm, selectedItem!!)) { openModal(block = true) }
                            if (modal.result)
                                this.refresh()
                        }
                        fun link() {
                            val o = selectedItem!!
                            val modal = find<PhrasesLinkView>("vm" to PhrasesLinkViewModel(o.wordid, o.word)) { openModal(block = true) }
                            if (modal.result)
                                tvPhrases.refresh()
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
                            item("Link Existing Phrases").action {
                                link()
                            }
                            separator()
                            item("Delete")
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
                    tvPhrases = tableview(vmPhrasesLang.lstPhrases) {
                        readonlyColumn("ID", MLangPhrase::id)
                        column("PHRASE", MLangPhrase::phraseProperty).makeEditable()
                        column("TRANSLATION", MLangPhrase::translationProperty).makeEditable()
                        onEditCommit {
                            val title = this.tableColumn.text
                            if (title == "Phrase")
                                rowValue.phrase = vmSettings.autoCorrectInput(rowValue.phrase)
                            vmPhrasesLang.update(rowValue).subscribe()
                        }
                        fun edit() {
                            // https://github.com/edvin/tornadofx/issues/226
                            val modal = find<PhrasesLangDetailView>("vmDetail" to PhrasesLangDetailViewModel(vmPhrasesLang, selectedItem!!)) { openModal(block = true) }
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
                                vmPhrasesLang.unlink(tvWords.selectedItem!!.wordid, selectedItem!!.id)
                            }
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
