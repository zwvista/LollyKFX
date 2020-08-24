package com.zwstudio.lolly.view

import com.zwstudio.lolly.view.misc.BlogScreen
import com.zwstudio.lolly.view.misc.DictsScreen
import com.zwstudio.lolly.view.misc.ReadNumberScreen
import com.zwstudio.lolly.view.misc.TextbooksScreen
import com.zwstudio.lolly.view.patterns.PatternsScreen
import com.zwstudio.lolly.view.phrases.PhasesLangScreen
import com.zwstudio.lolly.view.phrases.PhrasesReviewScreen
import com.zwstudio.lolly.view.phrases.PhrasesTextbookScreen
import com.zwstudio.lolly.view.phrases.PhrasesUnitScreen
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
                    AddTab<WordsSearchScreen>()
                }
                item("Settings","Ctrl+Alt+S")
                separator()
                item("Words in Unit","Ctrl+Alt+W").action {
                    AddTab<WordsUnitScreen>()
                }
                item("Phrases in Unit","Ctrl+Alt+P").action {
                    AddTab<PhrasesUnitScreen>()
                }
                separator()
                item("Words Review").action {
                    AddTab<WordsReviewScreen>()
                }
                item("Phrases Review").action {
                    AddTab<PhrasesReviewScreen>()
                }
                separator()
                item("Words in Textbook").action {
                    AddTab<WordsTextbookScreen>()
                }
                item("Phrases in Textbook").action {
                    AddTab<PhrasesTextbookScreen>()
                }
                separator()
                item("Words in Language").action {
                    AddTab<WordsLangScreen>()
                }
                item("Phrases in Language").action {
                    AddTab<PhasesLangScreen>()
                }
                separator()
                item("Patterns in Language").action {
                    AddTab<PatternsScreen>()
                }
            }
            menu("Tools") {
                item("Blog").action {
                    AddTab<BlogScreen>()
                }
                item("Read Number").action {
                    AddTab<ReadNumberScreen>()
                }
                item("Textbooks").action {
                    AddTab<TextbooksScreen>()
                }
                item("Dictionaries").action {
                    AddTab<DictsScreen>()
                }
                item("Test").action {
                    AddTab<PhrasesUnitScreen>()
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

