package com.zwstudio.lolly.view.phrases

import com.zwstudio.lolly.app.LollyApp
import com.zwstudio.lolly.data.misc.SettingsViewModel
import com.zwstudio.lolly.data.misc.copyText
import com.zwstudio.lolly.data.misc.googleString
import com.zwstudio.lolly.data.phrases.PhrasesLinkViewModel
import com.zwstudio.lolly.data.phrases.PhrasesUnitBatchViewModel
import com.zwstudio.lolly.data.phrases.PhrasesUnitDetailViewModel
import com.zwstudio.lolly.data.phrases.PhrasesUnitViewModel
import com.zwstudio.lolly.data.words.WordsLangDetailViewModel
import com.zwstudio.lolly.domain.wpp.MLangWord
import com.zwstudio.lolly.domain.wpp.MUnitPhrase
import com.zwstudio.lolly.view.words.WordsLangDetailView
import com.zwstudio.lolly.view.words.WordsLinkView
import javafx.application.Platform
import javafx.geometry.Orientation
import javafx.scene.control.TableRow
import javafx.scene.control.TableView
import javafx.scene.input.ClipboardContent
import javafx.scene.input.TransferMode
import javafx.scene.layout.Priority
import tornadofx.*

class PhrasesUnitView : PhrasesBaseView("Phrases in Unit") {
    var tvPhrases: TableView<MUnitPhrase> by singleAssign()
    var vm = PhrasesUnitViewModel(true)
    override val vmSettings get() = vm.vmSettings

    override val root = vbox {
        tag = this@PhrasesUnitView
        toolbarDicts = toolbar()
        toolbar {
            button("Add").action {
                val modal = find<PhrasesUnitDetailView>("vmDetail" to PhrasesUnitDetailViewModel(vm, vm.newUnitPhrase())) { openModal(block = true) }
                if (modal.result) {
                    vm.lstPhrasesAll.add(modal.vmDetail.item)
                    tvPhrases.refresh()
                }
            }
            button("Refresh").action {
                vm.reload()
            }
            button("Batch").action {
                val modal = find<PhrasesUnitBatchView>("vm" to PhrasesUnitBatchViewModel(vm)) { openModal(block = true) }
                if (modal.result) {
                    tvPhrases.refresh()
                }
            }
            button("Toggle") {
                isDisable = !vmSettings.toTypeIsMovable
            }.action {
                val item = tvPhrases.selectionModel.selectedItem
                val part = item?.part ?: vmSettings.lstParts[0].value
                vmSettings.toggleUnitPart(part).subscribe {
                    vm.reload()
                }
            }
            button("Previous") {
                isDisable = !vmSettings.toTypeIsMovable
            }.action {
                vmSettings.previousUnitPart().subscribe {
                    vm.reload()
                }
            }
            button("Next") {
                isDisable = !vmSettings.toTypeIsMovable
            }.action {
                vmSettings.nextUnitPart().subscribe {
                    vm.reload()
                }
            }
            button("Review")
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
                            vm.update(rowValue)
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
                            val modal = find<PhrasesUnitDetailView>("vmDetail" to PhrasesUnitDetailViewModel(vm, selectionModel.selectedItem)) { openModal(block = true) }
                            if (modal.result)
                                this.refresh()
                        }
                        fun link() {
                            val o = selectionModel.selectedItem!!
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
                            item("Link a New Word").action {
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
                        // https://stackoverflow.com/questions/28603224/sort-tableview-with-drag-and-drop-rows
                        setRowFactory { tv ->
                            val row = TableRow<MUnitPhrase>()
                            row.setOnDragDetected { event ->
                                if (!row.isEmpty && vm.vmSettings.isSinglePart && vm.noFilter) {
                                    val index = row.index
                                    val db = row.startDragAndDrop(TransferMode.MOVE)
                                    db.dragView = row.snapshot(null, null)
                                    val cc = ClipboardContent()
                                    cc[LollyApp.SERIALIZED_MIME_TYPE] = index
                                    db.setContent(cc)
                                    event.consume()
                                }
                            }
                            row.setOnDragOver { event ->
                                val db = event.dragboard
                                if (db.hasContent(LollyApp.SERIALIZED_MIME_TYPE)) {
                                    if (row.index != (db.getContent(LollyApp.SERIALIZED_MIME_TYPE) as Int).toInt()) {
                                        event.acceptTransferModes(*TransferMode.COPY_OR_MOVE)
                                        event.consume()
                                    }
                                }
                            }
                            row.setOnDragDropped { event ->
                                val db = event.dragboard
                                if (db.hasContent(LollyApp.SERIALIZED_MIME_TYPE)) {
                                    val draggedIndex = db.getContent(LollyApp.SERIALIZED_MIME_TYPE) as Int
                                    val draggedItem = items.removeAt(draggedIndex)
                                    val dropIndex = if (row.isEmpty) items.size else row.index
                                    items.add(dropIndex, draggedItem)
                                    event.isDropCompleted = true
                                    selectionModel.select(dropIndex)
                                    event.consume()
                                }
                            }
                            row
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
                            val modal = find<WordsLangDetailView>("vmDetail" to WordsLangDetailViewModel(vmWordsLang, selectionModel.selectedItem)) { openModal(block = true) }
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
