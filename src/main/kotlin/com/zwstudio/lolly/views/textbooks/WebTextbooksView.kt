package com.zwstudio.lolly.views.textbooks

import com.zwstudio.lolly.models.misc.MOnlineTextbook
import com.zwstudio.lolly.viewmodels.onlinetextbooks.OnlineTextbooksViewModel
import com.zwstudio.lolly.views.ILollySettings
import javafx.geometry.Orientation
import javafx.scene.control.TableView
import javafx.scene.layout.Priority
import javafx.scene.web.WebView
import tornadofx.*

class OnlineTextbooksView : Fragment("Textbooks"), ILollySettings {
    var tvTextbooks: TableView<MOnlineTextbook> by singleAssign()
    var wvWebPage: WebView by singleAssign()
    var vm = OnlineTextbooksViewModel()

    override val root = vbox {
        tag = this@OnlineTextbooksView
        toolbar {
            button("Refresh").action {
                vm.reload()
            }
        }
        splitpane(Orientation.HORIZONTAL) {
            vgrow = Priority.ALWAYS
            tvTextbooks = tableview(vm.lstItems) {
                vgrow = Priority.ALWAYS
                readonlyColumn("TEXTBOOKNAME", MOnlineTextbook::textbookname)
                readonlyColumn("UNIT", MOnlineTextbook::unit)
                readonlyColumn("TITLE", MOnlineTextbook::title)
                readonlyColumn("URL", MOnlineTextbook::url)
                readonlyColumn("ID", MOnlineTextbook::id)
                readonlyColumn("TEXTBOOKID", MOnlineTextbook::textbookid)
                onSelectionChange {
                    it?.url?.let {
                        wvWebPage.engine.load(it)
                    }
                }
            }
            wvWebPage = webview {

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
