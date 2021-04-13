package com.zwstudio.lolly.ui.patterns

import com.zwstudio.lolly.viewmodels.patterns.WebPageSelectViewModel
import com.zwstudio.lolly.models.wpp.MWebPage
import javafx.scene.control.TableView
import javafx.scene.layout.Priority
import tornadofx.*

class WebPageSelectView : Fragment("WebPage Select") {
    var tvWebPage: TableView<MWebPage> by singleAssign()
    val vm = WebPageSelectViewModel()
    var result = false

    override val root = vbox(10.0) {
        paddingAll = 10.0
        gridpane {
            hgap = 10.0
            vgap = 10.0
            constraintsForColumn(1).hgrow = Priority.ALWAYS
            row {
                label("TITLE:")
                textfield(vm.title)
            }
            row {
                label("URL:")
                textfield(vm.url)
            }
        }
        button("Search") {
            prefWidth = 150.0
        }.action {
            vm.search()
        }
        tvWebPage = tableview(vm.lstWebPages) {
            readonlyColumn("ID", MWebPage::id)
            column("TITLE", MWebPage::title)
            column("URL", MWebPage::url)
            onSelectionChange {
                vm.selectedWebPage.value = it
            }
        }
        buttonbar {
            button("OK") {
                isDefaultButton = true
                enableWhen { tvWebPage.selectionModel.selectedItemProperty().isNotNull }
            }.action {
                result = true
                close()
            }
            button("Cancel") {
                isCancelButton = true
            }.action {
                close()
            }
        }
    }

    init {
        vm.search()
    }
}
