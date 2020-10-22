package com.zwstudio.lolly.view.dicts

import com.zwstudio.lolly.app.LollyApp
import com.zwstudio.lolly.data.dicts.TransformEditViewModel
import com.zwstudio.lolly.data.dicts.TransformItemEditViewModel
import com.zwstudio.lolly.domain.misc.MTransformItem
import javafx.geometry.Orientation
import javafx.geometry.Pos
import javafx.scene.control.TableRow
import javafx.scene.control.TableView
import javafx.scene.input.ClipboardContent
import javafx.scene.input.TransferMode
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import javafx.scene.web.WebView
import tornadofx.*

class TransformEditView : Fragment("Transform Edit") {
    var tvTransformItems: TableView<MTransformItem> by singleAssign()
    val vm: TransformEditViewModel by param()
    var result = false
    var wvSource: WebView by singleAssign()
    var wvResult: WebView by singleAssign()
    var hbIntermediate: HBox by singleAssign()

    override val root = vbox(10.0) {
        paddingAll = 10.0
        splitpane(Orientation.HORIZONTAL) {
            vgrow = Priority.ALWAYS
            tvTransformItems = tableview(vm.lstTranformItems) {
                readonlyColumn("Index", MTransformItem::index)
                readonlyColumn("extractor", MTransformItem::extractor)
                readonlyColumn("replacement", MTransformItem::replacement)
                onDoubleClick {
                    val modal = find<TransformItemEditView>("vmEdit" to TransformItemEditViewModel(selectionModel.selectedItem)) { openModal(block = true) }
                    if (modal.result)
                        this.refresh()
                }
                // https://stackoverflow.com/questions/28603224/sort-tableview-with-drag-and-drop-rows
                setRowFactory { tv ->
                    val row = TableRow<MTransformItem>()
                    row.setOnDragDetected { event ->
                        val index = row.index
                        val db = row.startDragAndDrop(TransferMode.MOVE)
                        db.dragView = row.snapshot(null, null)
                        val cc = ClipboardContent()
                        cc[LollyApp.SERIALIZED_MIME_TYPE] = index
                        db.setContent(cc)
                        event.consume()
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
                            vm.reindex { tvTransformItems.refresh() }
                        }
                    }
                    row
                }
            }
            tabpane {
                vgrow = Priority.ALWAYS
                tab("Source") {
                    vbox {
                        hbox {
                            textfield(vm.sourceWord) {
                                promptText = "Word"
                            }
                            textfield(vm.vmDetail.url) {
                                hgrow = Priority.ALWAYS
                                promptText = "URL"
                            }
                            button("Get Html").action {
                                vm.getHtml().subscribe {
                                    onGetHtml()
                                }
                            }
                            button("Transform").action {
                                onTransformText()
                            }
                            button("Get & Transform").action {
                                vm.getHtml().subscribe {
                                    onGetHtml()
                                    onTransformText()
                                }
                            }
                        }
                        textarea(vm.sourceText) {
                            vgrow = Priority.ALWAYS
                        }
                        wvSource = webview {
                            vgrow = Priority.ALWAYS
                        }
                    }
                }
                tab("Result") {
                    splitpane(Orientation.VERTICAL) {
                        vgrow = Priority.ALWAYS
                        textarea(vm.resultText) {
                            vgrow = Priority.ALWAYS
                        }
                        wvResult = webview {
                            vgrow = Priority.ALWAYS
                        }
                    }
                }
                tab("Intermediate") {
                    vbox {
                        hbIntermediate = hbox {
                            alignment = Pos.CENTER
                        }
                        textarea(vm.intermediateText) {
                            vgrow = Priority.ALWAYS
                        }
                    }
                }
                tab("Template") {
                    vbox {
                        textarea(vm.vmDetail.template) {
                            vgrow = Priority.ALWAYS
                        }
                    }
                }
            }
        }
        buttonbar {
            button("OK") {
                isDefaultButton = true
            }.action {
                result = true
                vm.commit()
                close()
            }
            button("Cancel") {
                isCancelButton = true
            }.action {
                close()
            }
        }
    }

    private fun onGetHtml() {
        wvSource.engine.load(vm.sourceUrl)
    }
    private fun onTransformText() {
        vm.transformText()
        wvResult.engine.loadContent(vm.resultHtml)
        hbIntermediate.children.clear()
        hbIntermediate.children.add(spinner(0, vm.intermediateMaxIndex, property = vm.intermediateIndex))
    }
}
