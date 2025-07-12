package com.zwstudio.lolly.views.dicts

import com.zwstudio.lolly.models.misc.MDictionary
import com.zwstudio.lolly.viewmodels.dicts.DictsDetailViewModel
import com.zwstudio.lolly.viewmodels.dicts.DictsViewModel
import com.zwstudio.lolly.views.ILollySettings
import javafx.scene.control.TableView
import javafx.scene.layout.Priority
import tornadofx.*

class DictsView : Fragment("Dictionaries"), ILollySettings {
    var tvDictionaries: TableView<MDictionary> by singleAssign()
    var vm = DictsViewModel()

    override val root = vbox {
        tag = this@DictsView
        toolbar {
            button("Add").action {
                val modal = find<DictsDetailView>("vmDetail" to DictsDetailViewModel(vm, vm.newDictionary())) { openModal(block = true) }
                if (modal.result) {
                    vm.lstItems.add(modal.vmDetail.item)
                    tvDictionaries.refresh()
                }
            }
            button("Refresh").action {
                vm.reload()
            }
        }
        tvDictionaries = tableview(vm.lstItems) {
            vgrow = Priority.ALWAYS
            readonlyColumn("ID", MDictionary::id)
            readonlyColumn("LANGNAMETO", MDictionary::langnameto)
            readonlyColumn("SEQNUM", MDictionary::seqnum)
            readonlyColumn("DICTTYPENAME", MDictionary::dicttypename)
            readonlyColumn("DICTNAME", MDictionary::dictname)
            readonlyColumn("URL", MDictionary::url)
            readonlyColumn("CHCONV", MDictionary::chconv)
            readonlyColumn("AUTOMATION", MDictionary::automation)
            readonlyColumn("TRANSFORM", MDictionary::transform)
            readonlyColumn("WAIT", MDictionary::wait)
            readonlyColumn("TEMPLATE", MDictionary::template)
            readonlyColumn("TEMPLATE2", MDictionary::template2)
            onDoubleClick {
                val modal = find<DictsDetailView>("vmDetail" to DictsDetailViewModel(vm, selectedItem!!)) { openModal(block = true) }
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
