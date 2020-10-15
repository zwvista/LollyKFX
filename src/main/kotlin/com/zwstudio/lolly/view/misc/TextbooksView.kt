package com.zwstudio.lolly.view.misc

import com.zwstudio.lolly.data.misc.TextbooksDetailViewModel
import com.zwstudio.lolly.data.misc.TextbooksViewModel
import com.zwstudio.lolly.domain.misc.MTextbook
import com.zwstudio.lolly.view.ILollySettings
import javafx.scene.control.TableView
import javafx.scene.layout.Priority
import tornadofx.*

class TextbooksView : Fragment("Textbooks"), ILollySettings {
    var tvTextbooks: TableView<MTextbook> by singleAssign()
    var vm = TextbooksViewModel()

    override val root = vbox {
        tag = this@TextbooksView
        toolbar {
            button("Add").action {
                val modal = find<TextbooksDetailView>("vmDetail" to TextbooksDetailViewModel(vm, vm.newTextbook())) { openModal(block = true) }
                if (modal.result) {
                    vm.lstItems.add(modal.vmDetail.item)
                    tvTextbooks.refresh()
                }
            }
            button("Refresh").action {
                vm.reload()
            }
        }
        tvTextbooks = tableview(vm.lstItems) {
            vgrow = Priority.ALWAYS
            readonlyColumn("ID", MTextbook::id)
            readonlyColumn("TEXTBOOKNAME", MTextbook::textbookname)
            readonlyColumn("UNITS", MTextbook::units)
            readonlyColumn("PARTS", MTextbook::parts)
            onDoubleClick {
                val modal = find<TextbooksDetailView>("vmDetail" to TextbooksDetailViewModel(vm, selectionModel.selectedItem)) { openModal(block = true) }
                if (modal.result)
                    this.refresh()
            }
        }
        label(vm.statusText)
    }

    init {
        onSettingsChanged()
    }

    override fun onSettingsChanged() {
        vm.reload()
    }
}
