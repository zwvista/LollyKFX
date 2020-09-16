package com.zwstudio.lolly.view.misc

import com.zwstudio.lolly.data.misc.TextbooksViewModel
import com.zwstudio.lolly.domain.misc.MTextbook
import com.zwstudio.lolly.view.ILollySettings
import tornadofx.*

class TextbooksView : Fragment("Textbooks"), ILollySettings {
    var vm = TextbooksViewModel()

    override val root = vbox {
        tag = this@TextbooksView
        tableview(vm.lstItems) {
            readonlyColumn("ID", MTextbook::id)
            readonlyColumn("TEXTBOOKNAME", MTextbook::textbookname)
            readonlyColumn("UNITS", MTextbook::units)
            readonlyColumn("PARTS", MTextbook::parts)
        }
    }

    init {
        onSettingsChanged()
    }

    override fun onSettingsChanged() {
        vm.reload()
    }
}
