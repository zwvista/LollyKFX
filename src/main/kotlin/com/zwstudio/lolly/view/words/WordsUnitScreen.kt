package com.zwstudio.lolly.view.words

import com.zwstudio.lolly.app.LollyApp
import com.zwstudio.lolly.data.words.WordsUnitViewModel
import com.zwstudio.lolly.domain.MUnitWord
import javafx.geometry.Orientation
import javafx.scene.control.TableRow
import javafx.scene.input.ClipboardContent
import javafx.scene.input.TransferMode
import javafx.scene.layout.Priority
import tornadofx.*


class WordsUnitScreen : WordsBaseScreen("Words in Unit") {
    var vm = WordsUnitViewModel(true)
    override val vmSettings get() = vm.vmSettings

    override val root = vbox {
        tag = this@WordsUnitScreen
        toolbarDicts = toolbar()
        toolbar {
            button("Add")
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
        }
        splitpane(Orientation.HORIZONTAL) {
            vgrow = Priority.ALWAYS
            tableview(vm.lstWords) {
                readonlyColumn("UNIT", MUnitWord::unitstr)
                readonlyColumn("PART", MUnitWord::partstr)
                readonlyColumn("SEQNUM", MUnitWord::seqnum)
                column("WORD", MUnitWord::word).makeEditable()
                column("NOTE", MUnitWord::noteNotNull).makeEditable()
                readonlyColumn("LEVEL", MUnitWord::level)
                readonlyColumn("ACCURACY", MUnitWord::accuracy)
                readonlyColumn("WORDID", MUnitWord::wordid)
                readonlyColumn("ID", MUnitWord::id)
                readonlyColumn("FAMIID", MUnitWord::famiid)
                onEditCommit {
                    val title = this.tableColumn.text
                    if (title == "WORD") {
                        // https://stackoverflow.com/questions/29512142/how-do-i-restore-a-previous-value-in-javafx-tablecolumns-oneditcommit
                        rowValue.word = "ddddddddddd"
                        tableColumn.isVisible = false
                        tableColumn.isVisible = true
                    }
                }
                onSelectionChange {
                    onWordChanged(it?.word)
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
