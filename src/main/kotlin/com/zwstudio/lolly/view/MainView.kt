package com.zwstudio.lolly.view

import com.zwstudio.lolly.view.misc.*
import com.zwstudio.lolly.view.patterns.PatternsView
import com.zwstudio.lolly.view.phrases.PhrasesLangView
import com.zwstudio.lolly.view.phrases.PhrasesReviewView
import com.zwstudio.lolly.view.phrases.PhrasesTextbookView
import com.zwstudio.lolly.view.phrases.PhrasesUnitView
import com.zwstudio.lolly.view.words.*
import javafx.scene.control.TabPane
import javafx.scene.layout.Priority
import tornadofx.*

class MainView : View("Lolly TornadoFX") {
    var tabpane: TabPane by singleAssign()

    override val root = vbox {
        menubar {
            menu("Learn") {
                item("Search").action {
                    AddTab<WordsSearchView>()
                }
                item("Settings","Ctrl+Alt+S").action {
                    val modal = find<SettingsView> { openModal(block = true) }
                }
                separator()
                item("Words in Unit","Ctrl+Alt+W").action {
                    AddTab<WordsUnitView>()
                }
                item("Phrases in Unit","Ctrl+Alt+P").action {
                    AddTab<PhrasesUnitView>()
                }
                separator()
                item("Words Review").action {
                    AddTab<WordsReviewView>()
                }
                item("Phrases Review").action {
                    AddTab<PhrasesReviewView>()
                }
                separator()
                item("Words in Textbook").action {
                    AddTab<WordsTextbookView>()
                }
                item("Phrases in Textbook").action {
                    AddTab<PhrasesTextbookView>()
                }
                separator()
                item("Words in Language").action {
                    AddTab<WordsLangView>()
                }
                item("Phrases in Language").action {
                    AddTab<PhrasesLangView>()
                }
                separator()
                item("Patterns in Language").action {
                    AddTab<PatternsView>()
                }
            }
            menu("Tools") {
                item("Blog").action {
                    AddTab<BlogView>()
                }
                item("Read Number").action {
                    AddTab<ReadNumberView>()
                }
                item("Textbooks").action {
                    AddTab<TextbooksView>()
                }
                item("Dictionaries").action {
                    AddTab<DictsView>()
                }
                item("Test").action {
                    AddTab<PhrasesUnitView>()
                }
            }
        }
        tabpane = tabpane {
            vgrow = Priority.ALWAYS
        }
    }

    inline fun <reified Screen: Fragment> AddTab() {
        val t = tabpane.tabs.firstOrNull { it.content.tag is Screen } ?: tabpane.tab<Screen>()
        tabpane.selectionModel.select(t)
    }
}
