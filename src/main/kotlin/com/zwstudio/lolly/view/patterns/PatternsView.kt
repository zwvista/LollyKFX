package com.zwstudio.lolly.view.patterns

import com.zwstudio.lolly.data.misc.SettingsViewModel
import com.zwstudio.lolly.data.patterns.PatternsViewModel
import com.zwstudio.lolly.domain.wpp.MPattern
import com.zwstudio.lolly.domain.wpp.MPatternPhrase
import com.zwstudio.lolly.domain.wpp.MPatternWebPage
import com.zwstudio.lolly.view.ILollySettings
import javafx.geometry.Orientation
import javafx.scene.layout.Priority
import tornadofx.*

class PatternsView : Fragment("Patterns in Language"), ILollySettings {
    var vm = PatternsViewModel()

    override val root = vbox {
        tag = this@PatternsView
        toolbar {
            button("Add Pattern")
            button("Add Web Page")
            button("Refresh").action {
                vm.reload()
            }
            choicebox(vm.scopeFilter, SettingsViewModel.lstScopePatternFilters)
            textfield(vm.textFilter) {
                promptText = "Filter"
            }
        }
        splitpane(Orientation.HORIZONTAL) {
            vgrow = Priority.ALWAYS
            splitpane(Orientation.VERTICAL) {
                setDividerPosition(0, 0.8)
                tableview(vm.lstPatterns) {
                    readonlyColumn("ID", MPattern::id)
                    column("PATTERN", MPattern::pattern)
                    column("NOTE", MPattern::noteProperty)
                    column("TAGS", MPattern::tagsProperty)
                }
//                label(vm.statusText)
                tableview(vm.lstWebPages) {
                    readonlyColumn("ID", MPatternWebPage::id)
                    readonlyColumn("SEQNUM", MPatternWebPage::seqnum)
                    column("TITLE", MPatternWebPage::title)
                    column("URL", MPatternWebPage::url)
                    column("WEBPAGEID", MPatternWebPage::webpageid)
                }
            }
            splitpane(Orientation.VERTICAL) {
                setDividerPosition(0, 0.2)
                tableview(vm.lstPhrases) {
                    vgrow = Priority.ALWAYS
                    readonlyColumn("ID", MPatternPhrase::id)
                    column("PHRASE", MPatternPhrase::phrase)
                    column("TRANSLATION", MPatternPhrase::translation)
                }
                webview {

                }
            }
        }
    }

    init {
        onSettingsChanged()
    }

    override fun onSettingsChanged() {
        vm.reload()
    }
}
