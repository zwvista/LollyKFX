package com.zwstudio.lolly.ui.patterns

import com.zwstudio.lolly.app.LollyApp
import com.zwstudio.lolly.viewmodels.patterns.PatternsSplitViewModel
import com.zwstudio.lolly.models.wpp.MPattern
import com.zwstudio.lolly.models.wpp.MPatternVariation
import javafx.geometry.Orientation
import javafx.scene.control.TableRow
import javafx.scene.input.ClipboardContent
import javafx.scene.input.TransferMode
import javafx.scene.layout.Priority
import tornadofx.*

class PatternsSplitView : Fragment("Split Pattern") {
    val vmSplit : PatternsSplitViewModel by param()
    var result = false

    override val root = vbox(10.0) {
        splitpane(Orientation.VERTICAL) {
            vgrow = Priority.ALWAYS
            tableview(vmSplit.lstPatterns) {
                readonlyColumn("ID", MPattern::id)
                readonlyColumn("PATTERN", MPattern::pattern)
                readonlyColumn("NOTE", MPattern::note)
                readonlyColumn("TAGS", MPattern::tags)
            }
            tableview(vmSplit.lstPatternVariations) {
                readonlyColumn("", MPatternVariation::index)
                column("Variation", MPatternVariation::variation).makeEditable()
                // https://stackoverflow.com/questions/28603224/sort-tableview-with-drag-and-drop-rows
                setRowFactory { tv ->
                    val row = TableRow<MPatternVariation>()
                    row.setOnDragDetected { event ->
                        val index = row.index
                        val db = row.startDragAndDrop(TransferMode.MOVE)
                        db.dragView = row.snapshot(null, null)
                        val cc = ClipboardContent()
                        cc[LollyApp.SERIALIZED_MIME_TYPE] = index
                        db.setContent(cc)
                        event.consume()
                    }
                    onEditCommit {
                        vmSplit.mergeVariations()
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
                            vmSplit.reindex { refresh() }
                        }
                    }
                    row
                }
            }
        }
        form {
            fieldset {
                field("ID") {
                    textfield(vmSplit.id) {
                        isEditable = false
                    }
                }
                field("PATTERN") {
                    textfield(vmSplit.pattern)
                }
            }
            buttonbar {
                button("OK") {
                    isDefaultButton = true
                }.action {
                    result = true
                    vmSplit.commit()
                    close()
                }
                button("Cancel") {
                    isCancelButton = true
                }.action {
                    close()
                }
            }
        }
    }
}

