package com.zwstudio.lolly.ui

import com.zwstudio.lolly.app.LollyApp
import com.zwstudio.lolly.viewmodels.misc.Global
import com.zwstudio.lolly.viewmodels.misc.SettingsViewModel
import com.zwstudio.lolly.ui.dicts.DictsView
import com.zwstudio.lolly.ui.misc.BlogView
import com.zwstudio.lolly.ui.misc.LoginView
import com.zwstudio.lolly.ui.misc.ReadNumberView
import com.zwstudio.lolly.ui.misc.SettingsView
import com.zwstudio.lolly.ui.patterns.PatternsView
import com.zwstudio.lolly.ui.phrases.PhrasesLangView
import com.zwstudio.lolly.ui.phrases.PhrasesReviewView
import com.zwstudio.lolly.ui.phrases.PhrasesTextbookView
import com.zwstudio.lolly.ui.phrases.PhrasesUnitView
import com.zwstudio.lolly.ui.textbooks.TextbooksView
import com.zwstudio.lolly.ui.textbooks.WebTextbooksView
import com.zwstudio.lolly.ui.words.*
import javafx.application.Platform
import javafx.scene.control.TabPane
import javafx.scene.layout.Priority
import tornadofx.*
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.*


class MainView : View("Lolly TornadoFX") {
    var tabpane: TabPane by singleAssign()
    val vm: SettingsViewModel by inject()

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
                item("WebTextbooks").action {
                    AddTab<WebTextbooksView>()
                }
                item("Dictionaries").action {
                    AddTab<DictsView>()
                }
                item("Test").action {
                    AddTab<PhrasesUnitView>()
                }
                separator()
                item("Logout").action {
                    login()
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

    init {
        // https://stackoverflow.com/questions/51023378/setting-and-getting-parameters-in-a-javafx-application
        val props = Properties()
        val inStream = FileInputStream(LollyApp.configFile)
        props.loadFromXML(inStream)
        Global.userid = props.getProperty("userid")
        if (Global.userid.isEmpty())
            login()
        else
            setup()
    }

    fun setup() {
        vm.getData().subscribe {
            LollyApp.initializeObject.onNext(Unit)
            LollyApp.initializeObject.onComplete()
        }
    }

    fun login() {
        tabpane.tabs.clear()
        val props = Properties()
        props.setProperty("userid", "")
        props.setProperty("username", "")
        val outStream = FileOutputStream(LollyApp.configFile)
        props.storeToXML(outStream, "Configuration")
        val modal = find<LoginView> { openModal(block = true) }
        if (modal.result)
            setup()
        else
            Platform.exit();
    }

    fun searchNewWord(word: String) {
        AddTab<WordsSearchView>()
        (tabpane.selectionModel.selectedItem.content.tag as WordsSearchView).searchNewWord(word)
    }
}
