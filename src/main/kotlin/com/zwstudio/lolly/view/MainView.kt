package com.zwstudio.lolly.view

import com.zwstudio.lolly.view.misc.BlogScreen
import com.zwstudio.lolly.view.misc.ReadNumberScreen
import com.zwstudio.lolly.view.misc.TextbooksScreen
import com.zwstudio.lolly.view.patterns.PatternsScreen
import com.zwstudio.lolly.view.phrases.PhasesLangScreen
import com.zwstudio.lolly.view.phrases.PhrasesReviewScreen
import com.zwstudio.lolly.view.phrases.PhrasesTextbookScreen
import com.zwstudio.lolly.view.phrases.PhrasesUnitScreen
import com.zwstudio.lolly.view.words.WordsLangScreen
import com.zwstudio.lolly.view.words.WordsReviewScreen
import com.zwstudio.lolly.view.words.WordsTextbookScreen
import com.zwstudio.lolly.view.words.WordsUnitScreen
import javafx.scene.control.TabPane
import javafx.scene.layout.Priority
import tornadofx.*

class MainView : View("Lolly TornadoFX") {
    var tabpane: TabPane by singleAssign()

    override val root = vbox {
        menubar {
            menu("Learn") {
                item("Search").action {
                    AddTab<WordsUnitScreen>("Search")
                }
                item("Settings","Ctrl+Alt+S")
                separator()
                item("Words in Unit","Ctrl+Alt+W").action {
                    AddTab<WordsUnitScreen>("Words in Unit")
                }
                item("Phrases in Unit","Ctrl+Alt+P").action {
                    AddTab<PhrasesUnitScreen>("Phrases in Unit")
                }
                separator()
                item("Words Review").action {
                    AddTab<WordsReviewScreen>("Words Review")
                }
                item("Phrases Review").action {
                    AddTab<PhrasesReviewScreen>("Phrases Review")
                }
                separator()
                item("Words in Textbook").action {
                    AddTab<WordsTextbookScreen>("Words in Textbook")
                }
                item("Phrases in Textbook").action {
                    AddTab<PhrasesTextbookScreen>("Phrases in Textbook")
                }
                separator()
                item("Words in Language").action {
                    AddTab<WordsLangScreen>("Words in Language")
                }
                item("Phrases in Language").action {
                    AddTab<PhasesLangScreen>("Phrases in Language")
                }
                separator()
                item("Patterns in Language").action {
                    AddTab<PatternsScreen>("Patterns in Language")
                }
            }
            menu("Tools") {
                item("Blog").action {
                    AddTab<BlogScreen>("Blog")
                }
                item("Read Number").action {
                    AddTab<ReadNumberScreen>("Phrases in Unit")
                }
                item("Textbooks").action {
                    AddTab<TextbooksScreen>("Phrases in Unit")
                }
                item("Dictionaries").action {
                    AddTab<PhrasesUnitScreen>("Dictionaries")
                }
                item("Test").action {
                    AddTab<PhrasesUnitScreen>("Test")
                }
            }
        }
        tabpane = tabpane {
            vgrow = Priority.ALWAYS
        }
    }

    inline fun <reified Screen: Fragment> AddTab(text: String) {
        val t = tabpane.tabs.firstOrNull { it.text == text } ?: tabpane.tab<Screen>()
        tabpane.selectionModel.select(t)
    }
}

