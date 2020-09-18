package com.zwstudio.lolly.view.misc

import com.zwstudio.lolly.data.misc.DictsViewModel
import com.zwstudio.lolly.domain.misc.MDictionary
import com.zwstudio.lolly.view.ILollySettings
import javafx.scene.layout.Priority
import tornadofx.*

class DictsView : Fragment("Dictionaries"), ILollySettings {
    var vm = DictsViewModel()

    override val root = vbox {
        tag = this@DictsView
        tableview(vm.lstItems) {
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
