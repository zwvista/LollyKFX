package com.zwstudio.lolly.views.words

import com.zwstudio.lolly.views.LollyApp
import com.zwstudio.lolly.viewmodels.misc.SettingsViewModel
import com.zwstudio.lolly.viewmodels.misc.copyText
import com.zwstudio.lolly.viewmodels.misc.googleString
import com.zwstudio.lolly.viewmodels.phrases.PhrasesLangDetailViewModel
import com.zwstudio.lolly.viewmodels.phrases.PhrasesLinkViewModel
import com.zwstudio.lolly.viewmodels.words.WordsUnitBatchViewModel
import com.zwstudio.lolly.viewmodels.words.WordsUnitDetailViewModel
import com.zwstudio.lolly.viewmodels.words.WordsUnitViewModel
import com.zwstudio.lolly.models.wpp.MLangPhrase
import com.zwstudio.lolly.models.wpp.MUnitWord
import com.zwstudio.lolly.views.phrases.PhrasesLangDetailView
import com.zwstudio.lolly.views.phrases.PhrasesLinkView
import javafx.geometry.Orientation
import javafx.scene.control.TableRow
import javafx.scene.control.TableView
import javafx.scene.input.ClipboardContent
import javafx.scene.input.TransferMode
import javafx.scene.layout.Priority
import tornadofx.*

class WordsUnitView : WordsBaseView("Words in Unit") {
    var tvWords: TableView<MUnitWord> by singleAssign()
    var vm = WordsUnitViewModel(true)
    override val vmSettings get() = vm.vmSettings

    override val root = vbox {
        tag = this@WordsUnitView
        toolbarDicts = toolbar()
        toolbar {
            button("Add").action {
                val modal = find<WordsUnitDetailView>("vmDetail" to WordsUnitDetailViewModel(vm, vm.newUnitWord())) { openModal(block = true) }
                if (modal.result) {
                    vm.lstWordsAll.add(modal.vmDetail.item)
                    tvWords.refresh()
                }
            }
            button("Refresh").action {
                vm.reload()
            }
            button("Batch").action {
                val modal = find<WordsUnitBatchView>("vm" to WordsUnitBatchViewModel(vm)) { openModal(block = true) }
                if (modal.result)
                    tvWords.refresh()
            }
            button("Toggle") {
                isDisable = !vmSettings.toTypeIsMovable
            }.action {
                val item = tvWords.selectedItem
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
            checkbox("If Empty", vm.ifEmpty)
            button("Retrieve Notes").action {
                vm.retrieveNotes({}, {})
            }
            button("Clear Notes").action {
                vm.clearNotes({}, {})
            }
            button("Review")
            textfield(vm.newWord) {
                promptText = "New Word"
            }.action {
                vm.addNewWord().subscribe {
                    tvWords.refresh()
                    tvWords.selectionModel.select(tvWords.items.size - 1)
                }
            }
            choicebox(vm.scopeFilter, SettingsViewModel.lstScopeWordFilters)
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
                    tvWords = tableview(vm.lstWords) {
                        vgrow = Priority.ALWAYS
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
                            val modal = find<WordsUnitDetailView>("vmDetail" to WordsUnitDetailViewModel(vm, selectedItem!!)) { openModal(block = true) }
                            if (modal.result)
                                this.refresh()
                        }
                        fun link() {
                            val o = selectionModel.selectedItem!!
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
                            item("Retrieve Note").action {
                                vm.retrieveNote(selectedItem!!)
                            }
                            item("Clear Note").action {
                                vm.clearNote(selectedItem!!)
                            }
                            separator()
                            item("Edit").action {
                                edit()
                            }
                            item("Link a New Phrase").action {
                                edit()
                            }
                            item("Link Existing Phrases").action {
                                link()
                            }
                            separator()
                            item("Delete").action {
                                vm.clearNote(selectedItem!!)
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
                        // https://stackoverflow.com/questions/28603224/sort-tableview-with-drag-and-drop-rows
                        setRowFactory { tv ->
                            val row = TableRow<MUnitWord>()
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
                                    vm.reindex { refresh() }
                                }
                            }
                            row
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
