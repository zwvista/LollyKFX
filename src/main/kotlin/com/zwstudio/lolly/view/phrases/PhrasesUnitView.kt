package com.zwstudio.lolly.view.phrases

import com.zwstudio.lolly.app.LollyApp
import com.zwstudio.lolly.data.phrases.PhrasesUnitViewModel
import com.zwstudio.lolly.domain.wpp.MUnitPhrase
import com.zwstudio.lolly.domain.wpp.UnitPhraseViewModel
import javafx.geometry.Orientation
import javafx.scene.control.TableRow
import javafx.scene.input.ClipboardContent
import javafx.scene.input.TransferMode
import javafx.scene.layout.Priority
import tornadofx.*

class PhrasesUnitView : PhrasesBaseView("Phrases in Unit") {
    var vm = PhrasesUnitViewModel(true)
    override val vmSettings get() = vm.vmSettings

    override val root = vbox {
        tag = this@PhrasesUnitView
        toolbar {
            button("Add")
            button("Refresh").action {
                vm.reload()
            }
            button("Batch")
            button("Toggle")
            button("Previous")
            button("Review")
        }
        splitpane(Orientation.HORIZONTAL) {
            vgrow = Priority.ALWAYS
            tableview(vm.lstPhrases) {
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
                    val modal = find<PhrasesUnitDetailView>("model" to UnitPhraseViewModel(selectionModel.selectedItem)) { openModal(block = true) }
                    if (modal.result)
                        this.refresh()
                }
                // https://stackoverflow.com/questions/28603224/sort-tableview-with-drag-and-drop-rows
                setRowFactory { tv ->
                    val row = TableRow<MUnitPhrase>()
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