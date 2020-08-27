package com.zwstudio.lolly.view.words

import com.zwstudio.lolly.app.LollyApp
import com.zwstudio.lolly.data.words.WordsUnitViewModel
import com.zwstudio.lolly.domain.wpp.MUnitWord
import com.zwstudio.lolly.domain.wpp.UnitWordViewModel
import javafx.geometry.Orientation
import javafx.scene.control.TableRow
import javafx.scene.control.TableView
import javafx.scene.control.TextField
import javafx.scene.input.ClipboardContent
import javafx.scene.input.TransferMode
import javafx.scene.layout.Priority
import tornadofx.*


class WordsUnitView : WordsBaseView("Words in Unit") {
    var tvWords: TableView<MUnitWord> by singleAssign()
    var tfFilter: TextField by singleAssign()
    var vm = WordsUnitViewModel(true)
    override val vmSettings get() = vm.vmSettings

    override val root = vbox {
        tag = this@WordsUnitView
        toolbarDicts = toolbar()
        toolbar {
            button("Add").action {
                val modal = find<WordsUnitDetailView>("model" to UnitWordViewModel(vm.newUnitWord())) { openModal(block = true) }
                if (modal.result) {
                    vm.lstWords.add(modal.model.item)
                    tvWords.refresh()
                }
            }
            button("Refresh").action {
                vm.reload()
            }
            button("Batch")
            button("Toggle")
            button("Previous")
            checkbox("If Empty")
            button("Get Notes")
            button("Clear Notes")
            button("Review")
            textfield(vm.newWord) {
                promptText = "New Word"
                action {
                    val item = vm.newUnitWord().apply { word = vm.newWord.value }
                    vm.lstWords.add(item)
                    tvWords.refresh()
                }
            }
            choicebox(vm.scopeFilter, vmSettings.lstScopeWordFilters)
            textfield(vm.textFilter) {
                promptText = "Filter"
            }
            checkbox("Level >= 0", vm.levelge0only)
        }
        splitpane(Orientation.HORIZONTAL) {
            vgrow = Priority.ALWAYS
            tvWords = tableview(vm.lstWords) {
                readonlyColumn("UNIT", MUnitWord::unitstr)
                readonlyColumn("PART", MUnitWord::partstr)
                readonlyColumn("SEQNUM", MUnitWord::seqnum)
                column("WORD", MUnitWord::wordProperty).makeEditable()
                column("NOTE", MUnitWord::noteProperty).makeEditable()
                readonlyColumn("LEVEL", MUnitWord::level)
                readonlyColumn("ACCURACY", MUnitWord::accuracy)
                readonlyColumn("WORDID", MUnitWord::wordid)
                readonlyColumn("ID", MUnitWord::id)
                readonlyColumn("FAMIID", MUnitWord::famiid)
                onEditCommit {
                    val title = this.tableColumn.text
                    if (title == "WORD")
                        rowValue.word = vmSettings.autoCorrectInput(rowValue.word)
                }
                onSelectionChange {
                    onWordChanged(it?.word)
                }
                onDoubleClick {
                    // https://github.com/edvin/tornadofx/issues/226
                    val modal = find<WordsUnitDetailView>("model" to UnitWordViewModel(selectionModel.selectedItem)) { openModal(block = true) }
                    if (modal.result)
                        this.refresh()
                }
                // https://stackoverflow.com/questions/28603224/sort-tableview-with-drag-and-drop-rows
                setRowFactory { tv ->
                    val row = TableRow<MUnitWord>()
                    row.setOnDragDetected { event ->
                        if (!row.isEmpty) {
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
            splitpane(Orientation.VERTICAL) {
                dictsPane = tabpane {
                    vgrow = Priority.ALWAYS
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