package com.zwstudio.lolly.view.dicts

import com.zwstudio.lolly.data.dicts.TransformEditViewModel
import com.zwstudio.lolly.data.dicts.TransformItemEditViewModel
import com.zwstudio.lolly.domain.misc.MTransformItem
import javafx.geometry.Orientation
import javafx.geometry.Pos
import javafx.scene.layout.Priority
import tornadofx.*

class TransformEditView : Fragment("Transform Edit") {
    val vm: TransformEditViewModel by param()
    var result = false

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
                            textfield {
                                promptText = "Word"
                            }
                            textfield {
                                hgrow = Priority.ALWAYS
                                promptText = "URL"
                            }
                            button("Get Html") {

                            }
                            button("Transform") {

                            }
                            button("Get & Transform") {

                            }
                        }
                        textarea {
                            vgrow = Priority.ALWAYS
                        }
                        textarea {
                            vgrow = Priority.ALWAYS
                        }
                    }
                }
                tab("Result") {
                    vbox {
                        textarea {
                            vgrow = Priority.ALWAYS
                        }
                        textarea {
                            vgrow = Priority.ALWAYS
                        }
                    }
                }
                tab("Interim") {
                    vbox {
                        hbox {
                            alignment = Pos.CENTER
                            spinner(0, 10, property = vm.interimIndex)
                        }
                        textarea {
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
}
