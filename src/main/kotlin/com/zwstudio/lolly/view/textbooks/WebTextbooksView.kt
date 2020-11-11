package com.zwstudio.lolly.view.textbooks

import com.zwstudio.lolly.data.textbooks.TextbooksDetailViewModel
import com.zwstudio.lolly.data.textbooks.TextbooksViewModel
import com.zwstudio.lolly.data.textbooks.WebTextbooksViewModel
import com.zwstudio.lolly.domain.misc.MTextbook
import com.zwstudio.lolly.domain.misc.MWebTextbook
import com.zwstudio.lolly.domain.wpp.MPatternWebPage
import com.zwstudio.lolly.view.ILollySettings
import javafx.application.Platform
import javafx.geometry.Orientation
import javafx.scene.control.TableView
import javafx.scene.layout.Priority
import javafx.scene.web.WebView
import tornadofx.*

class WebTextbooksView : Fragment("Textbooks"), ILollySettings {
    var tvTextbooks: TableView<MWebTextbook> by singleAssign()
    var wvWebPage: WebView by singleAssign()
    var vm = WebTextbooksViewModel()

    override val root = vbox {
        tag = this@WebTextbooksView
        toolbar {
            button("Refresh").action {
                vm.reload()
            }
        }
        splitpane(Orientation.HORIZONTAL) {
            vgrow = Priority.ALWAYS
            tvTextbooks = tableview(vm.lstItems) {
                vgrow = Priority.ALWAYS
                readonlyColumn("TEXTBOOKNAME", MWebTextbook::textbookname)
                readonlyColumn("UNIT", MWebTextbook::unit)
                readonlyColumn("TITLE", MWebTextbook::title)
                readonlyColumn("URL", MWebTextbook::url)
                readonlyColumn("ID", MWebTextbook::id)
                readonlyColumn("TEXTBOOKID", MWebTextbook::textbookid)
                readonlyColumn("WEBPAGEID", MWebTextbook::webpageid)
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
