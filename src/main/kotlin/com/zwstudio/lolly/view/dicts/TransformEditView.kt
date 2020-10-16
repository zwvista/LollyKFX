package com.zwstudio.lolly.view.dicts

import com.zwstudio.lolly.data.dicts.TransformEditViewModel
import com.zwstudio.lolly.data.dicts.TransformItemEditViewModel
import com.zwstudio.lolly.domain.misc.MTransformItem
import javafx.geometry.Orientation
import javafx.geometry.Pos
import javafx.scene.control.Spinner
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import javafx.scene.web.WebView
import tornadofx.*

class TransformEditView : Fragment("Transform Edit") {
    val vm: TransformEditViewModel by param()
    var result = false
    var wvSource: WebView by singleAssign()
    var wvResult: WebView by singleAssign()
    var hbInterim: HBox by singleAssign()
    var spInterim: Spinner<Int>? = null

    override val root = vbox(10.0) {
        paddingAll = 10.0
        splitpane(Orientation.HORIZONTAL) {
            vgrow = Priority.ALWAYS
            tableview(vm.lstTranformItems) {
                readonlyColumn("Index", MTransformItem::index)
                readonlyColumn("extractor", MTransformItem::extractor)
                readonlyColumn("replacement", MTransformItem::replacement)
                onDoubleClick {
                    val modal = find<TransformItemEditView>("vmEdit" to TransformItemEditViewModel(selectionModel.selectedItem)) { openModal(block = true) }
                    if (modal.result)
                        this.refresh()
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
                                isEditable = false
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
                tab("Interim") {
                    vbox {
                        hbInterim = hbox {
                            alignment = Pos.CENTER
                        }
                        textarea(vm.interimText) {
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
                action {
                    result = true
                    vm.commit()
                    close()
                }
            }
            button("Cancel") {
                isCancelButton = true
                action {
                    close()
                }
            }
        }
    }

    private fun onGetHtml() {
        wvSource.engine.load(vm.sourceUrl)
    }
    private fun onTransformText() {
        vm.transformText()
        wvResult.engine.loadContent(vm.resultHtml)
        if (spInterim != null) hbInterim.children.remove(spInterim)
        spInterim = spinner(0, vm.interimMaxIndex, property = vm.interimIndex)
        hbInterim.children.add(spInterim)
    }
}
